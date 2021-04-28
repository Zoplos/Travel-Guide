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