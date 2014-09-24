package org.aksw.index;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class SPARQLIndex extends Index
{
	private final QueryExecutionFactory qef;

	protected String queryTemplate = "SELECT DISTINCT ?uri WHERE {\n" +
			"?uri a ?type.\n" + 
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d OFFSET %d";
	
	protected String queryWithLabelTemplate = "SELECT DISTINCT ?uri ?label WHERE {\n" +
			"?uri a ?type.\n" + 
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d OFFSET %d";	
	
	public SPARQLIndex(QueryExecutionFactory qef) {this.qef=qef;}
	
	@Override
	public IndexResultSet getResourcesWithScores(String searchTerm, int limit) {
		IndexResultSet irs = new IndexResultSet();
		
		String query = String.format(queryWithLabelTemplate, searchTerm, limit);
		
		ResultSet rs = qef.createQueryExecution(query).execSelect();
		
		QuerySolution qs;
		while(rs.hasNext()){
			qs = rs.next();
			RDFNode uriNode = qs.get("uri");
			if(uriNode.isURIResource()){
				RDFNode labelNode = qs.get("label");
				
				String uri = uriNode.asResource().getURI();
				String label = labelNode.asLiteral().getLexicalForm();
				irs.add(new IndexItem(uri, label, 1f));
			}
		}		
		return irs;
	}
	
}