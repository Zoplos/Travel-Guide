package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import exceptions.AgeException;
import exceptions.WikipediaException;

/* TODO: CLEAN UP MAIN PLS WHAT IS THIS, 
 * maybe consider making terms and geodesic vectors 
 * vector elements instead of arrays*/

public class Main {
	
	
	
	private static Scanner scanner;

	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException, AgeException {
		
		String appid = "f6dcc229d7417350c66a4ebd183e128c";
		
		City city1 = new City("Athens","gr",new int[10],new double[2]);
		city1.RetrieveData(appid);
		
		City city2 = new City("Berlin","de",new int[10],new double[2]);
		city2.RetrieveData(appid);
		
		City city3 = new City("Tokyo","jp",new int[10],new double[2]);
		city3.RetrieveData(appid);
		
		City city4 = new City("London","uk",new int[10],new double[2]);
		city4.RetrieveData(appid);
		
		City city5 = new City("Amsterdam","nl",new int[10],new double[2]);
		city5.RetrieveData(appid);
		
		City city6 = new City("Paris","fr",new int[10],new double[2]);
		city6.RetrieveData(appid);
		
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(city1);
		cities.add(city2);
		cities.add(city3);
		cities.add(city4);
		cities.add(city5);
		cities.add(city6);
							
		YoungTraveller traveller = new YoungTraveller("John",123,new int[] {0,27,41,4,1,3,0,6,15,11},new double[] {52.5244,13.4105});
		MiddleTraveller traveller1 = new MiddleTraveller("Jason",123,new int[] {5,12,3,6,65,23,1,6,8,10},new double[] {35.6895,139.6917});
		ElderTraveller traveller2 = new ElderTraveller("Nick",123,new int[] {4,22,7,12,6,10,1,2,3,4},new double[] {51.5085,-0.1257});	
		
		ArrayList<Traveller> travellers = new ArrayList<Traveller>();
		travellers.add(traveller);
		travellers.add(traveller1);
		travellers.add(traveller2);
		
		System.out.println("\nSimilarity of certain city for all Travellers:\n");
		for(int i=0;i<travellers.size();i++) {
			travellers.get(i).setSimilarity(travellers.get(i).calculate_similarity(city3, travellers.get(i), 0.5));
			System.out.println(travellers.get(i).getSimilarity() + " Traveller: " + travellers.get(i).getName());
		}
		
		for(int k = 0; k<cities.size();k++) {
			cities.get(k).setSimilarity(traveller.calculate_similarity(cities.get(k), traveller, 0.2));
		}
				
		System.out.println("\nSimilarity of each city for Young Traveller:\n");
		for(int k = 0; k<cities.size();k++) {
			System.out.println(cities.get(k).getSimilarity() + " " +cities.get(k).getName());
		}
		
		Collections.sort(cities);
		
		System.out.println("\nSorted similarity of each city for Young Traveller:\n");
		for(int k = 0; k<cities.size();k++) {
			System.out.println(cities.get(k).getSimilarity() + " " +cities.get(k).getName());
		} 
		
		System.out.println("\nCompare cities results:\n");
				
		System.out.println(traveller.compare_cities(cities).getName());
		ArrayList<City> test2 = traveller.compare_cities(cities, 5);
		
		for(int i = 0; i<test2.size();i++) {
			System.out.println(test2.get(i).getName());
		}
		
		System.out.println("\nFree ticket to Tokyo, polymorphism example:\n");
		for(int i=0;i<travellers.size();i++) {
			travellers.get(i).setSimilarity(travellers.get(i).calculate_similarity(city3, travellers.get(i), 0.5));
			System.out.println(travellers.get(i).getSimilarity() + " Traveller: " + travellers.get(i).getName());
		}
		Collections.sort(travellers);
		System.out.println("\nFree ticket to Tokyo goes to: " + travellers.get(0).getName());
		
		
		scanner = new Scanner(System.in);
		System.out.println("Please enter your age: ");
		int age = scanner.nextInt();
		if(age>=16 && age<=25) {
			System.out.println("TODO: add young traveller");
		} else if(age>25 && age <=60) {
			System.out.println("TODO: add middle traveller");
		} else if(age>60 && age<=115) {
			System.out.println("TODO: add elder traveller");
		} else throw new AgeException();
				
	}
	
}
