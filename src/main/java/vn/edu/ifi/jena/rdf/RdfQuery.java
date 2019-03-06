package vn.edu.ifi.jena.rdf;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
public class RdfQuery {
	
	@RequestMapping(value={ "/", "/search"})
	public String sparql() {
		
		
		return "index";
	}

	public static void main(String[] args) {
	   System.out.print("\n Entrer le nom d'une ville : " );
	   Scanner sc=new Scanner(System.in);  
	   String ville =sc.next();  
	   ville = Character.toUpperCase(ville.charAt(0))+ville.substring(1);
		  // TODO Auto-generated method stub
		String service = "http://dbpedia.org/sparql";
        String queryString = "";
        queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> SELECT ?label " +
            "WHERE {" +
             "<http://dbpedia.org/resource/"+ville+"> <http://dbpedia.org/ontology/country> ?y ."+
             "?y rdfs:label ?label ."+ 
             "FILTER (LANG(?label) = 'en')"+
            "}";
	    Query query = QueryFactory.create(queryString);
	    QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(service, query);
	    ResultSet results = qexec.execSelect();
	    // write to a ByteArrayOutputStream
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    ResultSetFormatter.outputAsJSON(outputStream, results);

	    // and turn that into a String
	    String json = new String(outputStream.toByteArray());
		 System.out.println(json);
		
	   
	}

}
