package org.aksw.rdfindex;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;

public class SPARQLClassesIndex extends SPARQLIndex
{
	public SPARQLClassesIndex(QueryExecutionFactory qef) {super(qef);}

	static final String QUERY_TEMPLATE = "SELECT DISTINCT ?uri WHERE {\n" +
			"?s a ?uri.\n" +
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d";

	static final String QUERY_TEMPLATE_WITH_LABEL = "SELECT DISTINCT ?uri ?label WHERE {\n" +
			"?s a ?uri.\n" +
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d";

	@Override protected String getQueryTemplate() {return QUERY_TEMPLATE;}
	@Override protected String getQueryTemplateWithLabel() {return QUERY_TEMPLATE_WITH_LABEL;}
}