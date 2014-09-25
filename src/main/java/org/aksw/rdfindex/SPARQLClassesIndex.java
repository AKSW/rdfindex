package org.aksw.rdfindex;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;

public class SPARQLClassesIndex extends SPARQLIndex
{
	public SPARQLClassesIndex(QueryExecutionFactory qef) {super(qef);}

	{
	queryTemplate = "SELECT DISTINCT ?uri WHERE {\n" +
			"?s a ?uri.\n" + 
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d OFFSET %d";

	queryWithLabelTemplate = "SELECT DISTINCT ?uri ?label WHERE {\n" +
			"?s a ?uri.\n" + 
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d OFFSET %d";
	}
}