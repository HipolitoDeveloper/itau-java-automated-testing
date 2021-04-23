package br.com.automation.APIRest;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.internal.util.IOUtils;

import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestAPI {

	
	@Test
	public void Teste() throws IOException {
	  String uriBase = "https://viacep.com.br/ws/{cep}/json";
	  
	  ObjectMapper mapper = new ObjectMapper();
	  Path path = Paths.get("entrada.txt");    	 
	  String txtLinhas = "";	
	  
	  List<Entrada> dados = new ArrayList<Entrada>();
	  
        for (String linha : Files.readAllLines(path)) {
        	txtLinhas += linha ;
        }	        
    
        dados = mapper.readValue(txtLinhas, new TypeReference<List<Entrada>>(){});
        
        for(Entrada dado: dados) {
        	given().relaxedHTTPSValidation()
    		.pathParam("cep", dado.getCep())
    		.when()
    		.get(uriBase)		
    		.then()
    		.statusCode(200) 
    		.contentType(ContentType.JSON);		
        }     
	
	}
}
