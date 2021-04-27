package main;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.AgeException;
import exceptions.WikipediaException;

/*TODO: Remove unnecessary lines of presentation code in main.*/

public class Ergasia {

	static Connection db_con_obj = null;
	static PreparedStatement db_prep_obj = null;
	private static Scanner scanner;
	static Date date = new Date();
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException, AgeException, SQLException {
		
		Ergasia tester = new Ergasia();
		scanner = new Scanner(System.in);
		
		makeJDBCConnection();
		ReadData();
		City city1 = new City("Athens","gr",new int[10],new double[2]);
		city1.RetrieveData();
		
		City city2 = new City("Berlin","de",new int[10],new double[2]);
		city2.RetrieveData();
		
		City city3 = new City("Tokyo","jp",new int[10],new double[2]);
		city3.RetrieveData();
		
		City city4 = new City("London","uk",new int[10],new double[2]);
		city4.RetrieveData();
		
		City city5 = new City("Amsterdam","nl",new int[10],new double[2]);
		city5.RetrieveData();
		
		City city6 = new City("Paris","fr",new int[10],new double[2]);
		city6.RetrieveData();
		
		HashMap<String, City> citiesHm = new HashMap<String, City>();
		citiesHm.put("Athens", city1);
		citiesHm.put("Berlin", city2);
		citiesHm.put("Tokyo", city3);
		citiesHm.put("London", city4);
		citiesHm.put("Amsterdam", city5);
		citiesHm.put("Paris", city6);
		
		ArrayList<City> cities = new ArrayList<City>();
		Set<?> set = citiesHm.entrySet();
		Iterator<?> iter = set.iterator();		
		
		while(iter.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry)iter.next();
	        System.out.print("key: "+me.getKey() + ". ");
	        System.out.print("Values: "+me.getValue().toString()+ ". ");
	        cities.add((City) me.getValue());
	    }
		System.out.println("\n"); 
		for(int i =0; i<cities.size();i++) {
			
			System.out.println(cities.get(i).toString());
		}
		
		ArrayList<Traveller> travellers = new ArrayList<Traveller>();
		try {
			travellers = tester.readJson();
		} catch (JsonParseException e) {
	         e.printStackTrace();
	    } catch (JsonMappingException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    }
				
//		System.out.println("\nSimilarity of certain city for all Travellers:\n");
//		for(int i=0;i<travellers.size();i++) {
//			travellers.get(i).setSimilarity(travellers.get(i).calculate_similarity(city3, travellers.get(i), 0.5));
//			System.out.println(travellers.get(i).getSimilarity() + " Traveller: " + travellers.get(i).getName());
//		}
//		
//		for(int k = 0; k<cities.size();k++) {
//			cities.get(k).setSimilarity(travellers.get(0).calculate_similarity(cities.get(k), travellers.get(0), 0.2));
//		}
//				
//		System.out.println("\nSimilarity of each city for Young Traveller:\n");
//		for(int k = 0; k<cities.size();k++) {
//			System.out.println(cities.get(k).getSimilarity() + " " +cities.get(k).getName());
//		}
//		
//		Collections.sort(cities);
//		
//		System.out.println("\nSorted similarity of each city for Young Traveller:\n");
//		for(int k = 0; k<cities.size();k++) {
//			System.out.println(cities.get(k).getSimilarity() + " " +cities.get(k).getName());
//		} 
		
		
		//Method overloading example
		System.out.println("\nCompare cities results:\n");
				
		System.out.println(travellers.get(0).compare_cities(cities).getName());
		ArrayList<City> test2 = travellers.get(0).compare_cities(cities, 5);
		
		for(int i1 = 0; i1<test2.size();i1++) {
			System.out.println(test2.get(i1).getName());
		}
		
		//Working polymorphism example using abstract method "calculate_similarity"
		System.out.println("\nFree ticket to Tokyo, polymorphism example:\n");
		for(int i=0;i<travellers.size();i++) {
			travellers.get(i).setSimilarity(travellers.get(i).calculate_similarity(city3, travellers.get(i), 0.5));
			System.out.println(travellers.get(i).getSimilarity() + " Traveller: " + travellers.get(i).getName());
		}
		Collections.sort(travellers, Traveller.simComparator);
		System.out.println("\nFree ticket to Tokyo goes to: " + travellers.get(0).getName());
		
		//Traveller Creation method for each possible age,
		//throws exception if the age is not correct
		System.out.println("\nCreate new traveller? [y/n]");
		String answer = scanner.next().toLowerCase();
		if(answer.equals("yes")|| answer.equals("y")) {
			System.out.println("\nPlease enter your age: ");
			int age = scanner.nextInt();
			if(age>=16 && age<=25) {
				YoungTraveller newTraveller = new YoungTraveller();
				newTraveller = (YoungTraveller) travellerCreation(newTraveller);
				travellers.add(newTraveller);
				tester.writeJSON(travellers);
			} else if(age>25 && age <=60) {
				MiddleTraveller newTraveller = new MiddleTraveller();
				newTraveller = (MiddleTraveller) travellerCreation(newTraveller);
				travellers.add(newTraveller);
				tester.writeJSON(travellers);
			} else if(age>60 && age<=115) {
				ElderTraveller newTraveller = new ElderTraveller();
				newTraveller = (ElderTraveller) travellerCreation(newTraveller);
				travellers.add(newTraveller);
				tester.writeJSON(travellers);
			} else throw new AgeException();
		}
		
		// Presenting travellers without duplicates sorted by their timestamp
		presentTravellers(travellers);
		searchCity(travellers.get(0), citiesHm, travellers, cities);
		
		

		//System.out.println("\nTraveller timestamp after searchCity method: " + traveller.getTimestamp());
		presentTravellers(travellers);
		
	}
	
	
	
	public static void searchCity(Traveller traveller, HashMap<String, City> citiesHm, ArrayList<Traveller> travellers, ArrayList<City> cities) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException {		
		System.out.println("\nEnter city name:");
		String cityName = scanner.next();			
		if(citiesHm.containsKey(cityName)) {
			System.out.println("City: " + cityName + " exists!");
		} else {			
			System.out.println("\nWhich country is your city in?");
			String cityCountry = scanner.next();
			City city = new City(cityName, cityCountry,new int[10],new double[2]);
			city.RetrieveData();
			citiesHm.put(cityName, city);
			cities.add(city);
		}
		System.out.println("\nTraveller class: "+traveller.getClass());
		long timestamp = date.getTime();
		System.out.print("\n" + timestamp);
		traveller.setTimestamp(timestamp);
	}
	
	public static void presentTravellers(ArrayList<Traveller> travellers) {
		ArrayList<Traveller> noDupsTrav = new ArrayList<Traveller>();
		Collections.sort(travellers, Traveller.NameTimeComparator);
		
		for(int i=0;i<travellers.size();i++) {
			boolean flag = false;
			for(int j = 0; j < noDupsTrav.size(); j++) {
				
				if(travellers.get(i).getName().equals(noDupsTrav.get(j).getName())) {
					flag = true;
				}				
			}
			if(flag == false) {
				noDupsTrav.add(travellers.get(i));
			}
		}		
		Collections.sort(noDupsTrav,Traveller.timeComparator);
		for(int i = 0; i < noDupsTrav.size();i++) {
			System.out.println("Traveller's name : " + noDupsTrav.get(i).getName() + ", timestamp: " + noDupsTrav.get(i).getTimestamp());
		}
	}
	
	public void writeJSON(ArrayList<Traveller> in_arraylist) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		AllTravellers data = new AllTravellers();
		data.setCollectionAllTravellers(in_arraylist);
		mapper.writeValue(new File("arraylist_travellers.json"), data);
	}
	
	public ArrayList<Traveller> readJson() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		AllTravellers data = mapper.readValue(new File("arraylist_travellers.json"), AllTravellers.class);
		return data.getCollectionAllTravellers();
	}
	private static Traveller travellerCreation(Traveller traveller) {
		Date date = new Date();
		System.out.println("\nEnter your full name: ");
		String name = scanner.next();
		traveller.setName(name);
		System.out.println("\nEnter your phone number: ");
		int phone = scanner.nextInt();
		traveller.setPhone(phone);
		traveller.setTimestamp(date.getTime());
		int cafe = ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int sea = ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int museum = ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int restaurant= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int stadium= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int cinema= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int mountain= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int lake= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int river= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		int bar= ThreadLocalRandom.current().nextInt(0, 30 + 1);
		traveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
		System.out.println("\nAdd your longitude: ");
		double lon = scanner.nextDouble();
		System.out.println("\nAdd your latitude: ");
		double lat = scanner.nextDouble();
		traveller.setGeodesic_vector(new double[] {lon,lat});
		return traveller;
	}
	
	private static void makeJDBCConnection() {
		 
		try {//We check that the DB Driver is available in our project.		
			Class.forName("oracle.jdbc.driver.OracleDriver"); //This code line is to check that JDBC driver is available. Or else it will throw an exception. Check it with 2. 
			System.out.println("Congrats - Seems your oracle JDBC Driver Registered!"); 
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
			db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl","it21826","it21826");// Returns a connection to the URL.
			//Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
			if (db_con_obj != null) { 
				System.out.println("Connection Successful! Enjoy. Now it's time to CRUD data. ");
				
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Oracle Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}
	
	private static void ReadData() throws SQLException {
		db_prep_obj = db_con_obj.prepareStatement("select * from cities");
		ResultSet  rs = db_prep_obj.executeQuery();

	    while (rs.next()){
	    	String city_name = rs.getString("CITY_NAME");
	    	String country = rs.getString("COUNTRY");
	    	double lat = rs.getDouble("LAT");
	        double lon = rs.getDouble("LON");
	        int term_1 = rs.getInt("CAFE");
	        int term_2 = rs.getInt("SEA");
	        int term_3 = rs.getInt("MUSEUM");
	        int term_4 = rs.getInt("RESTAURANT");
	        int term_5 = rs.getInt("STADIUM");
	        int term_6 = rs.getInt("CINEMA");
	        int term_7 = rs.getInt("MOUNTAIN");
	        int term_8 = rs.getInt("LAKE");
	        int term_9 = rs.getInt("RIVER");
	        int term_10 = rs.getInt("BAR");

	        System.out.println("The data are: "+ city_name + " " + country + " "+ lat+ " "+ lon+ " "+ term_1+ " "+ term_2+ " "+ term_3+ " "+ term_4+ " "+ term_5
	        		+ " "+ term_6+ " "+ term_7+ " "+ term_8+ " "+ term_9+ " "+ term_10);
	        
	    }
	}
}
