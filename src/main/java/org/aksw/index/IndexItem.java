package org.aksw.index;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Sorted from highest to lowest score, then by uri. For instance, SortedSet.first() gives an item with the highest score. */
@Getter
@AllArgsConstructor
public class IndexItem implements Comparable<IndexItem>
{	
	private String uri;
	private String label;
	private float score;
	
	@Override public int compareTo(IndexItem item)
	{
		if(uri.equals(item.uri)) return 0; // items are treated as unique by uri
		
		int i = -Float.compare(score, item.score);
		if(i==0) {i=uri.compareTo(item.uri);}
		return i;
	}

	@Override public int hashCode()
	{
		return uri.hashCode();
	}

	@Override public boolean equals(Object obj)
	{
		if(!(obj instanceof IndexItem)) return false;
		return uri.equals(((IndexItem)obj).uri);
	}

}