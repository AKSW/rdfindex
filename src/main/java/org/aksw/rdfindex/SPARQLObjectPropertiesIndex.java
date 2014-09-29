package org.aksw.rdfindex;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;

public class SPARQLObjectPropertiesIndex extends SPARQLPropertiesIndex
{	
	public SPARQLObjectPropertiesIndex(QueryExecutionFactory qef) {super(qef);}

	private static final String QUERY_TEMPLATE = "SELECT ?uri WHERE {\n" +
			//				"?s ?uri ?o.\n" + 
			"?uri a owl:ObjectProperty." + 
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s', 'i'))}\n" +
			"LIMIT %d";

	private static final String QUERY_TEMPLATE_WITH_LABEL = "PREFIX owl:<http://www.w3.org/2002/07/owl#>  SELECT DISTINCT ?uri ?label WHERE {\n" +
			//				"?s ?uri ?o.\n" + 
			"?uri a owl:ObjectProperty." + 
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s', 'i'))}\n" +
			"LIMIT %d";

	@Override protected String getQueryTemplate() {return QUERY_TEMPLATE;}
	@Override protected String getQueryTemplateWithLabel() {return QUERY_TEMPLATE_WITH_LABEL;}

}