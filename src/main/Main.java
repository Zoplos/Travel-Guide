package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Main {
	
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		
		String appid = "f6dcc229d7417350c66a4ebd183e128c";
		
		City city1 = new City("Athens","gr",new int[10],new double[2]);
		city1.RetrieveData(city1.getName(), city1.getCountry(),appid,city1.getTerms_vector(),city1.getGeodesic_vector());
		
		City city2 = new City("Berlin","de",new int[10],new double[2]);
		city2.RetrieveData(city2.getName(), city2.getCountry(),appid,city2.getTerms_vector(),city2.getGeodesic_vector());
		
		City city3 = new City("Tokyo","jp",new int[10],new double[2]);
		city1.RetrieveData(city3.getName(), city3.getCountry(),appid,city3.getTerms_vector(),city3.getGeodesic_vector());
		
		City city4 = new City("London","uk",new int[10],new double[2]);
		city4.RetrieveData(city4.getName(), city4.getCountry(),appid,city4.getTerms_vector(),city4.getGeodesic_vector());
		
		/*for(int i=0;i<2;i++) {
			System.out.println(city1.getGeodesic_vector()[i]);
		}
		
		for(int i=0;i<10;i++) {
			if(i != 9) {
				System.out.print(city1.getTerms_vector()[i] + ",");
			} else {
				System.out.print(city1.getTerms_vector()[i] + "\n");
			}
		}*/
				
		
		
		/*for(int i=0;i<2;i++) {
			System.out.println(city2.getGeodesic_vector()[i]);
		}
		
		for(int i=0;i<10;i++) {
			if(i != 9) {
				System.out.print(city2.getTerms_vector()[i] + ",");
			} else {
				System.out.print(city2.getTerms_vector()[i] + "\n");
			}
			
		}*/
		
		
		MiddleTraveller traveller = new MiddleTraveller("John",123,new int[10],new double[2]);
		traveller.fillWithData(traveller.getTerms_vector(), traveller.getGeodesic_vector());
		System.out.println(traveller.calculate_similarity(city1, traveller, 0));
		System.out.println(traveller.calculate_similarity(city2, traveller, 1));		
		
		city1.setSimilarity(traveller.calculate_similarity(city1, traveller, 0.2));
		city2.setSimilarity(traveller.calculate_similarity(city2, traveller, 0.2));
		city3.setSimilarity(traveller.calculate_similarity(city3, traveller, 0.2));
		city4.setSimilarity(traveller.calculate_similarity(city4, traveller, 0.2));
		System.out.println(city1.getSimilarity());
		System.out.println(city2.getSimilarity());
		System.out.println(city3.getSimilarity());
		System.out.println(city4.getSimilarity());
		
		ArrayList<City> cities = new ArrayList<City>(10);
		cities.add(city1);
		cities.add(city2);
		cities.add(city3);
		cities.add(city4);
		
		Collections.sort(cities,City.CitySimComparator);
		System.out.println(cities.get(0).getSimilarity() + cities.get(0).getName());
		System.out.println(cities.get(1).getSimilarity() + cities.get(1).getName());
		System.out.println(cities.get(2).getSimilarity() + cities.get(2).getName());
		System.out.println(cities.get(3).getSimilarity() + cities.get(3).getName());
		
		System.out.println(traveller.compare_cities(cities).getName());
	}
	
}
