rdfindex
========

Create and Acess RDF indexes over literals including fuzzy matching and WordNet synonyms.

RDFIndex can be used with a SPARQL endpoint, a Jena model or a SOLR/Lucene index.
Creating an index for a SPARQL endpoint is easiest to set up but is  time and memory consuming for large knowledge bases such as DBpedia and LinkedGeoData, where only indexing of the ontology (classes, properties) is feasible. If you want to create resource indexes over large knowlege bases, create a SOLR index and use the SOLRIndex class.

**Usage Examples**

***Fuzzy Index on LinkedGeoData Classes***

    SPARQLModelIndex index = SPARQLModelIndex.createClassIndex
      ("http://linkedgeodata.org/sparql", "http://linkedgeodata.org",0.5f);
    assertTrue(index.getResources("mirporg").get(0).equals("http://linkedgeodata.org/ontology/Airport"));

***Exact Index with WordNet Synonyms on LinkedGeoData Classes***    
    
    SPARQLModelIndex index = SPARQLModelIndex.createClassIndex
    ("http://linkedgeodata.org/sparql",         "http://linkedgeodata.org",1);
    WordNetIndex wIndex = new WordNetIndex(index);
    assertTrue(wIndex.getResources("cities").contains("http://linkedgeodata.org/ontology/City"));
    assertTrue(wIndex.getResources("aerodromes").contains("http://linkedgeodata.org/ontology/Airport"));
