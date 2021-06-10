import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import exceptions.AgeException;
import main.ElderTraveller;
import main.MiddleTraveller;
import main.Traveller;
import main.YoungTraveller;

//IN MAIN
		//ADDING NEW CITIES
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
		
		addDataToDB(city1);
		addDataToDB(city2);
		addDataToDB(city3);
		addDataToDB(city4);
		addDataToDB(city5);
		addDataToDB(city6);

	HashMap<String, City> citiesHm = new HashMap<String,City>();
	citiesHm.put("Athens", city1);
	itiesHm.put("Berlin", city2);
	citiesHm.put("Tokyo", city3);
	citiesHm.put("London", city4);
	citiesHm.put("Amsterdam", city5);
	citiesHm.put("Paris", city6);

	//PRESENTATION CODE FOR COMPARING CITIES
		System.out.println("\nSimilarity of certain city for all Travellers:\n");
		for(int i=0;i<travellers.size();i++) {
			travellers.get(i).setSimilarity(travellers.get(i).calculate_similarity(city3, travellers.get(i), 0.5));
			System.out.println(travellers.get(i).getSimilarity() + " Traveller: " + travellers.get(i).getName());
		}
		
		for(int k = 0; k<cities.size();k++) {
			cities.get(k).setSimilarity(travellers.get(0).calculate_similarity(cities.get(k), travellers.get(0), 0.2));
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
		//Traveller Creation method for each possible age,
				//throws exception if the age is incorrect
				System.out.println("\nCreate new traveller? [y/n]");
				String answer = scanner.next().toLowerCase();
				if(answer.equals("yes")|| answer.equals("y")) {
					System.out.println("\nPlease enter your age: ");
					int age = scanner.nextInt();
					if(age>=16 && age<=25) {
						YoungTraveller newTraveller = new YoungTraveller();
						newTraveller = (YoungTraveller) createTraveller(newTraveller);
						travellers.add(newTraveller);
						tester.writeJSON(travellers);
					} else if(age>25 && age <=60) {
						MiddleTraveller newTraveller = new MiddleTraveller();
						newTraveller = (MiddleTraveller) createTraveller(newTraveller);
						travellers.add(newTraveller);
						tester.writeJSON(travellers);
					} else if(age>60 && age<=115) {
						ElderTraveller newTraveller = new ElderTraveller();
						newTraveller = (ElderTraveller) createTraveller(newTraveller);
						travellers.add(newTraveller);
						tester.writeJSON(travellers);
					} else throw new AgeException();
				}	
		private static Traveller createTraveller(Traveller traveller) {
			Date date = new Date();
			
			//Information gathering
			System.out.println("\nEnter your full name: ");
			scanner.nextLine();
			String name = scanner.nextLine();
			traveller.setName(name);
			System.out.println("\nEnter your phone number: ");
			int phone = scanner.nextInt();
			traveller.setPhone(phone);
			traveller.setTimestamp(date.getTime());
			
			//Random generation of terms vector
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
		
		//Searching for a certain city || adding new to db
		System.out.println("\nSearch for a city? [y/n]");
		String answerCitySearch = scanner.next().toLowerCase();
		if(answerCitySearch.equals("yes") || answerCitySearch.equals("y")) {
			searchCity(travellers.get(0), citiesHm, travellers, cities);
		}
		
		// Presenting travellers without duplicates sorted by their timestamp
		System.out.println("\nNo Duplicate travellers:");
		ArrayList<Traveller> noDupsTrav = presentTravellers(travellers);					
		for(int i = 0; i < noDupsTrav.size();i++) {
		System.out.println("Traveller's name : " + noDupsTrav.get(i).getName() + ", timestamp: " + noDupsTrav.get(i).getTimestamp());
		}
		
//		public static void searchCity(Traveller traveller, HashMap<String, City> citiesHm, ArrayList<Traveller> travellers, ArrayList<City> cities) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException {		
//			System.out.println("\nEnter city name:");
//			scanner.nextLine();
//			String cityName = scanner.nextLine();
//			
//			//if city name is not a key in the hashmap, uses the api to search for the city
//			if(citiesHm.containsKey(cityName)) {
//				System.out.println("City: " + cityName + " exists!");
//			} else {			
//				System.out.println("\nWhich country is your city in?");
//				String cityCountry = scanner.next();
//				City city = new City(cityName, cityCountry,new int[10],new double[2]);
//				city.RetrieveData();
//				citiesHm.put(cityName, city);
//				cities.add(city);
//				addDataToDB(city);
//			}
//			
//			long timestamp = date.getTime();
//			
//			//Creating a copy of the traveller that used the method
//			if(traveller.getClass().getTypeName() == "main.YoungTraveller") {
//				YoungTraveller newTrav = new YoungTraveller();
//				newTrav = (YoungTraveller) copyTraveller(traveller,newTrav,timestamp);
//				travellers.add(newTrav);
//			} else if(traveller.getClass().getTypeName() == "main.MiddleTraveller") {
//				MiddleTraveller newTrav = new MiddleTraveller();
//				newTrav = (MiddleTraveller) copyTraveller(traveller,newTrav,timestamp);
//				travellers.add(newTrav);
//			} else if(traveller.getClass().getTypeName() == "main.ElderTraveller") {
//				ElderTraveller newTrav = new ElderTraveller();
//				newTrav = (ElderTraveller) copyTraveller(traveller,newTrav,timestamp);
//				travellers.add(newTrav);
//			}
//			tester.writeJSON(travellers);
//		}
		
		