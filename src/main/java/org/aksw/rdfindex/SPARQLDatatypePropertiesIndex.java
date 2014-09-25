package org.aksw.rdfindex;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;

public class SPARQLDatatypePropertiesIndex extends SPARQLPropertiesIndex
{
	{
		queryTemplate = "SELECT ?uri WHERE {\n" +
//				"?s ?uri ?o.\n" + 
				"?uri a owl:DatatypeProperty." + 
				"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
				"FILTER(REGEX(STR(?label), '%s', 'i'))}\n" +
				"LIMIT %d OFFSET %d";
		
		queryWithLabelTemplate = "PREFIX owl:<http://www.w3.org/2002/07/owl#>  SELECT DISTINCT ?uri ?label WHERE {\n" +
//				"?s ?uri ?o.\n" + 
				"?uri a owl:DatatypeProperty." + 
				"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
				"FILTER(REGEX(STR(?label), '%s', 'i'))}\n" +
				"LIMIT %d OFFSET %d";
	}	
		
	public SPARQLDatatypePropertiesIndex(QueryExecutionFactory qef) {super(qef);}
}