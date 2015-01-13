package org.aksw.rdfindex;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class SPARQLIndex extends Index
{
	private final QueryExecutionFactory qef;

	static final String QUERY_TEMPLATE = "SELECT DISTINCT ?uri WHERE {\n" +
			"?uri a ?type.\n" +
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d";

	static final String QUERY_TEMPLATE_WITH_LABEL = "SELECT DISTINCT ?uri ?label WHERE {\n" +
			"?uri a ?type.\n" +
			"?uri <http://www.w3.org/2000/01/rdf-schema#label> ?label\n" +
			"FILTER(REGEX(STR(?label), '%s'))}\n" +
			"LIMIT %d";

	protected String getQueryTemplate() {return QUERY_TEMPLATE;}
	protected String getQueryTemplateWithLabel() {return QUERY_TEMPLATE_WITH_LABEL;}

	public SPARQLIndex(QueryExecutionFactory qef) {this.qef=qef;}

	@Override
	public IndexResultSet getResourcesWithScores(String searchTerm, int limit) {
		IndexResultSet irs = new IndexResultSet();

		String query = String.format(QUERY_TEMPLATE_WITH_LABEL, searchTerm, limit);

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