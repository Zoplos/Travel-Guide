package main;

import java.io.IOException;
import java.net.MalformedURLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Main {
	
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		String appid = "f6dcc229d7417350c66a4ebd183e128c";
		City city1 = new City("Athens","gr",new int[10],new double[2]);
		city1.RetrieveData(city1.getName(), city1.getCountry(),appid,city1.getTerms_vector(),city1.getGeodesic_vector());
		System.out.println(city1.getGeodesic_vector()[0]);
		System.out.println(city1.getGeodesic_vector()[1]);
		System.out.println(city1.getTerms_vector()[0]);
	}
}
