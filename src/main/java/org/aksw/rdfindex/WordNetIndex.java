package org.aksw.rdfindex;

import java.util.HashSet;
import java.util.Set;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

public class WordNetIndex extends Index
{
	static final Dictionary d;
//	static final MorphologicalProcessor morphy;
	static
	{
		try	{d = Dictionary.getDefaultResourceInstance();}
		catch (JWNLException e) {throw new RuntimeException(e);}
	}

	final Index index;	

	protected static final float WORDNET_PENALTY_FACTOR = 0.9f; // wild guess, modify empirically later

	public WordNetIndex(Index index) {this.index=index;}

	@Override public IndexResultSet getResourcesWithScores(String queryString, int limit)
	{		
		IndexResultSet resources = index.getResourcesWithScores(queryString, limit);
		if(resources.size()>=limit&&resources.last().getScore()==1f) return resources; // already perfect, no wordnet necessary
		int MAX_ADDITIONS = resources.tailSet(new IndexItem("","",WORDNET_PENALTY_FACTOR)).size();
				
//		(new Comparator<IndexItem>(){public int compare(IndexItem i1, IndexItem i2)
//		{return i1.getUri().compareTo(i2.getUri());};});
//		itemsByUri.addAll(resources);

		try
		{		
			Set<Synset> synsets = new HashSet<>();			
			
			for(IndexWord iw: d.lookupAllIndexWords(queryString).getIndexWordArray())
			{
				synsets.addAll(iw.getSenses());
			}
			
			Set<String> lemmas = new HashSet<>();
			
			for(Synset synset: synsets)
			{
				for(Word word: synset.getWords()) {lemmas.add(word.getLemma());}
			}
			
			for(String lemma: lemmas) {resources.addAll(index.getResourcesWithScores(lemma, MAX_ADDITIONS));}				
			
			resources.retainBest(limit);
			return resources;
		}
		catch (JWNLException e) {throw new RuntimeException(e);}
	}

}