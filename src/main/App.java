package main;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.AgeException;
import exceptions.WikipediaException;


/*TODO: Remove unnecessary lines of presentation code in main.*/

public class App {

	static Connection db_con_obj = null;
	static PreparedStatement db_prep_obj = null;
	static Date date = new Date();
	static App tester = new App();
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException, AgeException, SQLException {
		
		
		DBConnection.makeJDBCConnection();
		
		HashMap<String, City> citiesHm = DBConnection.LoadData();
		
		ArrayList<City> cities = new ArrayList<City>();
		Set<?> set = citiesHm.entrySet();
		Iterator<?> iter = set.iterator();		
		
		while(iter.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry)iter.next();	        
	        cities.add((City) me.getValue());
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
		
		//Method overloading example
		System.out.println("\nCompare cities results:\n");
				
		System.out.println(travellers.get(0).compare_cities(cities, 0.2));
		ArrayList<City> test2 = travellers.get(0).compare_cities(cities,0.2,3);
		
		for(int i1 = 0; i1<test2.size();i1++) {
			System.out.println(test2.get(i1).getName());
		}
		
		//Working polymorphism example using abstract method "calculate_similarity"
		System.out.println("\nFree ticket to Tokyo, polymorphism example:\n");
		for(int i=0;i<travellers.size();i++) {
			travellers.get(i).setSimilarity(travellers.get(i).calculate_similarity(citiesHm.get("Tokyo"), travellers.get(i), 0.23));
			System.out.println(travellers.get(i).getSimilarity() + " Traveller: " + travellers.get(i).getName());
		}
		Collections.sort(travellers, Traveller.simComparator);
		System.out.println("\nFree ticket to Tokyo goes to: " + travellers.get(0).getName());
		
		ArrayList<Traveller> noDupsTrav = presentTravellers(travellers);
		
		GraphicsUI.GUI(noDupsTrav,cities,travellers,citiesHm);
						
		Optional<RecommendedCity> recommendedCity = 
				noDupsTrav.stream().map(i->new RecommendedCity(i.getVisit(),innerDot(i.getTerms_vector(),noDupsTrav.get(10).getTerms_vector()))).max(Comparator.comparingInt(RecommendedCity::getRank));
		
		System.out.println("The recommended city is: "+recommendedCity.get().getName());

	}
	
	public static Traveller copyTraveller(Traveller oldTrav, Traveller newTrav, long timestamp) {
		newTrav.setGeodesic_vector(oldTrav.getGeodesic_vector());
		newTrav.setName(oldTrav.getName());
		newTrav.setPhone(oldTrav.getPhone());
		newTrav.setTerms_vector(oldTrav.getTerms_vector());
		newTrav.setTimestamp(timestamp);
		return newTrav;
	}
	
	public static ArrayList<Traveller> presentTravellers(ArrayList<Traveller> travellers) {
		ArrayList<Traveller> noDupsTrav = new ArrayList<Traveller>();
		Collections.sort(travellers, Traveller.NameTimeComparator);
		//flag determines if the traveller is already in the list or not
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
		return noDupsTrav;
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
	

	protected static int innerDot(int[] currentTraveller,int[] candidateTraveller) {
		int sum=0;
		for(int i=0;i<currentTraveller.length;i++)
			sum+=currentTraveller[i]*candidateTraveller[i];
		return sum;
	}
}


