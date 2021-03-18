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
		
		for(int i=0;i<2;i++) {
			System.out.println(city1.getGeodesic_vector()[i]);
		}
		
		for(int i=0;i<10;i++) {
			if(i != 9) {
				System.out.print(city1.getTerms_vector()[i] + ",");
			} else {
				System.out.print(city1.getTerms_vector()[i] + "\n");
			}
		}
				
		City city2 = new City("Berlin","de",new int[10],new double[2]);
		city2.RetrieveData(city2.getName(), city2.getCountry(),appid,city2.getTerms_vector(),city2.getGeodesic_vector());
		
		for(int i=0;i<2;i++) {
			System.out.println(city2.getGeodesic_vector()[i]);
		}
		
		for(int i=0;i<10;i++) {
			if(i != 9) {
				System.out.print(city2.getTerms_vector()[i] + ",");
			} else {
				System.out.print(city2.getTerms_vector()[i] + "\n");
			}
			
		}
				
		MiddleTraveller traveller = new MiddleTraveller("John",123,new int[10],new double[2]);
		traveller.fillWithData(traveller.getTerms_vector(), traveller.getGeodesic_vector());
		System.out.println(traveller.calculate_similarity(city1, traveller, 0));
		System.out.println(traveller.calculate_similarity(city2, traveller, 1));
	}
}
