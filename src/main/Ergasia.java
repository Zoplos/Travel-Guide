package main;


import java.awt.GridLayout;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.AgeException;
import exceptions.WikipediaException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/*TODO: Remove unnecessary lines of presentation code in main.*/

public class Ergasia {

	static Connection db_con_obj = null;
	static PreparedStatement db_prep_obj = null;
	private static Scanner scanner;
	static Date date = new Date();
	static Ergasia tester = new Ergasia();
	static JFrame f;  
	private static JPanel panel1;
	private static JPanel panel2;
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException, AgeException, SQLException {
		
		
		scanner = new Scanner(System.in);
		makeJDBCConnection();
		
		HashMap<String, City> citiesHm = LoadData();
		
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
		
		// Presenting travellers without duplicates sorted by their timestamp
		System.out.println("\nNo Duplicate travellers:");
		ArrayList<Traveller> noDupsTrav = presentTravellers(travellers);					
		for(int i = 0; i < noDupsTrav.size();i++) {
		System.out.println("Traveller's name : " + noDupsTrav.get(i).getName() + ", timestamp: " + noDupsTrav.get(i).getTimestamp());
		}
		
		GUI(noDupsTrav,cities);
		//Searching for a certain city || adding new to db
		System.out.println("\nSearch for a city? [y/n]");
		String answerCitySearch = scanner.next().toLowerCase();
		if(answerCitySearch.equals("yes") || answerCitySearch.equals("y")) {
			searchCity(travellers.get(0), citiesHm, travellers, cities);
		}
						
		//System.out.println("\nTraveller timestamp after searchCity method: " + traveller.getTimestamp());
		//presentTravellers(travellers);
		
	}
		
	public static void searchCity(Traveller traveller, HashMap<String, City> citiesHm, ArrayList<Traveller> travellers, ArrayList<City> cities) throws JsonParseException, JsonMappingException, MalformedURLException, IOException, WikipediaException {		
		System.out.println("\nEnter city name:");
		scanner.nextLine();
		String cityName = scanner.nextLine();
		
		//if city name is not a key in the hashmap, uses the api to search for the city
		if(citiesHm.containsKey(cityName)) {
			System.out.println("City: " + cityName + " exists!");
		} else {			
			System.out.println("\nWhich country is your city in?");
			String cityCountry = scanner.next();
			City city = new City(cityName, cityCountry,new int[10],new double[2]);
			city.RetrieveData();
			citiesHm.put(cityName, city);
			cities.add(city);
			addDataToDB(city);
		}
		
		long timestamp = date.getTime();
		
		//Creating a copy of the traveller that used the method
		if(traveller.getClass().getTypeName() == "main.YoungTraveller") {
			YoungTraveller newTrav = new YoungTraveller();
			newTrav = (YoungTraveller) copyTraveller(traveller,newTrav,timestamp);
			travellers.add(newTrav);
		} else if(traveller.getClass().getTypeName() == "main.MiddleTraveller") {
			MiddleTraveller newTrav = new MiddleTraveller();
			newTrav = (MiddleTraveller) copyTraveller(traveller,newTrav,timestamp);
			travellers.add(newTrav);
		} else if(traveller.getClass().getTypeName() == "main.ElderTraveller") {
			ElderTraveller newTrav = new ElderTraveller();
			newTrav = (ElderTraveller) copyTraveller(traveller,newTrav,timestamp);
			travellers.add(newTrav);
		}
		tester.writeJSON(travellers);
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
	
	protected static HashMap<String, City> LoadData() throws SQLException {
		db_prep_obj = db_con_obj.prepareStatement("select * from cities");
		ResultSet  rs = db_prep_obj.executeQuery();
		HashMap<String,City> cities = new HashMap<String,City>();
		
		
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
	        double geo[] = {lat,lon};
	    	int terms[]= {term_1,term_2,term_3,term_4,term_5,term_6,term_7,term_8,term_9,term_10};
	    	
	    	cities.put(city_name, new City(city_name,country,terms,geo));
//	        System.out.println("The data are: "+ city_name + " " + country + " "+ lat+ " "+ lon+ " "+ term_1+ " "+ term_2+ " "+ term_3+ " "+ term_4+ " "+ term_5
//	        		+ " "+ term_6+ " "+ term_7+ " "+ term_8+ " "+ term_9+ " "+ term_10);
	    }
	    return cities;
	}
	
	private static void addDataToDB(City city) {
		 
		try {
			String insertQueryStatement = "INSERT  INTO  CITIES  VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";			
			db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
			db_prep_obj.setString(1, city.getName());//.setInt(1, newKey);//.setString
			db_prep_obj.setString(2, city.getCountry());
			db_prep_obj.setDouble(3, city.getGeodesic_vector()[0]);//.setInt(2, year);
			db_prep_obj.setDouble(4, city.getGeodesic_vector()[1]);
			db_prep_obj.setInt(5, city.getTerms_vector()[0]);
			db_prep_obj.setInt(6, city.getTerms_vector()[1]);
			db_prep_obj.setInt(7, city.getTerms_vector()[2]);
			db_prep_obj.setInt(8, city.getTerms_vector()[3]);
			db_prep_obj.setInt(9, city.getTerms_vector()[4]);
			db_prep_obj.setInt(10, city.getTerms_vector()[5]);
			db_prep_obj.setInt(11, city.getTerms_vector()[6]);
			db_prep_obj.setInt(12, city.getTerms_vector()[7]);
			db_prep_obj.setInt(13, city.getTerms_vector()[8]);
			db_prep_obj.setInt(14, city.getTerms_vector()[9]);			
			// execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
			int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
			System.out.println("Rows "+numRowChanged+" changed.");
			
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
	}
	private static void GUI(ArrayList<Traveller> noDupsTrav,ArrayList<City> cities) throws JsonParseException, JsonMappingException, IOException, SQLException{  
	    f=new JFrame();  
	    JTextArea textArea1=new JTextArea("main text");  
	    textArea1.setBounds(10,30, 200,200);  
	    
	    JTextArea textArea2=new JTextArea("visit text");  
	    textArea2.setBounds(10,30, 200,200);  
	    
	    JTextArea textArea3=new JTextArea("help text");  
	    textArea3.setBounds(10,30, 200,200);  
	    
	    
	    itemTabPanel1(noDupsTrav); 
	    itemTabPanel2(noDupsTrav,cities);
//	    JPanel p2=new JPanel();  
//	    p2.add(textArea2);
	    JPanel p3=new JPanel();
	    p3.add(textArea3);
	    
	    
	    JTabbedPane tp=new JTabbedPane();  
	    tp.setBounds(0,0,600,300);  
	    //tp.addTab("main",p1);
	    tp.add("Traveller Creation",panel1);  
	    tp.add("Visit",panel2);  
	    tp.add("Add City",p3);    
	    f.add(tp);  
	    f.setSize(400,400);  
	    f.setLayout(null);  
	    f.setVisible(true);  
	}
	
	public static void itemTabPanel1(ArrayList<Traveller> travellers) {
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(9,4));
		
		JLabel nameLabel = new JLabel("Name: ");
		panel1.add(nameLabel);
		nameLabel.setBounds(10,10,0,0);
		JTextField nameTf = new JTextField();
		panel1.add(nameTf);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		panel1.add(phoneLabel);
		
		JTextField phoneTf = new JTextField();
		panel1.add(phoneTf);
		
		JLabel ageLabel = new JLabel("Age: ");
		panel1.add(ageLabel);
		
		JTextField ageTf = new JTextField();
		panel1.add(ageTf);
		JLabel nullLabel = new JLabel("");
		panel1.add(nullLabel);
		JLabel nullLabel2 = new JLabel("");
		panel1.add(nullLabel2);
		
		JLabel latLabel = new JLabel("Latitude: ");
		panel1.add(latLabel);
		
		JTextField latTf = new JTextField();
		panel1.add(latTf);
		
		JLabel lonLabel = new JLabel("Longitude: ");
		panel1.add(lonLabel);
		
		JTextField lonTf = new JTextField();
		panel1.add(lonTf);
		
		Integer[] ratings = {1,2,3,4,5,6,7,8,9,10};

		JComboBox<Integer> comboBox1 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox2 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox3 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox4 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox5 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox6 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox7 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox8 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox9 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox10 = new JComboBox<>(ratings);
		
		JLabel term1Label = new JLabel("Cafe: ");
		panel1.add(term1Label);
		panel1.add(comboBox1);
		JLabel term2Label = new JLabel("Sea: ");
		panel1.add(term2Label);
		panel1.add(comboBox2);
		JLabel term3Label = new JLabel("Museum: ");
		panel1.add(term3Label);
		panel1.add(comboBox3);
		JLabel term4Label = new JLabel("Restaurant: ");
		panel1.add(term4Label);
		panel1.add(comboBox4);
		JLabel term5Label = new JLabel("Stadium: ");
		panel1.add(term5Label);
		panel1.add(comboBox5);
		JLabel term6Label = new JLabel("Cinema: ");
		panel1.add(term6Label);
		panel1.add(comboBox6);
		JLabel term7Label = new JLabel("Mountain: ");
		panel1.add(term7Label);
		panel1.add(comboBox7);
		JLabel term8Label = new JLabel("Lake: ");
		panel1.add(term8Label);
		panel1.add(comboBox8);
		JLabel term9Label = new JLabel("River: ");
		panel1.add(term9Label);
		panel1.add(comboBox9);
		JLabel term10Label = new JLabel("Bar: ");
		panel1.add(term10Label);
		panel1.add(comboBox10);
		
		JButton addBtn = new JButton("Add Traveller");
		panel1.add(addBtn);
		
		
		addBtn.addMouseListener(new MouseAdapter(){			
			public void mouseClicked(MouseEvent e) {
				int age = Integer.valueOf(ageTf.getText()).intValue();
				if(age>=16 && age<=25) {
					YoungTraveller newTraveller = new YoungTraveller();
					String name = nameTf.getText();
					int phone = Integer.valueOf(phoneTf.getText()).intValue();
					newTraveller.setName(name);
					newTraveller.setPhone(phone);
					newTraveller.setTimestamp(date.getTime());
					
					int cafe = (Integer) comboBox1.getSelectedItem();
					int sea = (Integer) comboBox2.getSelectedItem();
					int museum = (Integer) comboBox3.getSelectedItem();
					int restaurant= (Integer) comboBox4.getSelectedItem();
					int stadium= (Integer) comboBox5.getSelectedItem();
					int cinema= (Integer) comboBox6.getSelectedItem();
					int mountain= (Integer) comboBox7.getSelectedItem();
					int lake= (Integer) comboBox8.getSelectedItem();
					int river= (Integer) comboBox9.getSelectedItem();
					int bar= (Integer) comboBox10.getSelectedItem();
					
					newTraveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
					double lon = Double.valueOf(lonTf.getText()).doubleValue();
					double lat = Double.valueOf(latTf.getText()).doubleValue();
					newTraveller.setGeodesic_vector(new double[] {lon,lat});
					travellers.add(newTraveller);
					try {
						tester.writeJSON(travellers);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(age>25 && age <=60) {
					MiddleTraveller newTraveller = new MiddleTraveller();
					String name = nameTf.getText();
					int phone = Integer.valueOf(phoneTf.getText()).intValue();
					newTraveller.setName(name);
					newTraveller.setPhone(phone);
					newTraveller.setTimestamp(date.getTime());
					
					int cafe = (Integer) comboBox1.getSelectedItem();
					int sea = (Integer) comboBox2.getSelectedItem();
					int museum = (Integer) comboBox3.getSelectedItem();
					int restaurant= (Integer) comboBox4.getSelectedItem();
					int stadium= (Integer) comboBox5.getSelectedItem();
					int cinema= (Integer) comboBox6.getSelectedItem();
					int mountain= (Integer) comboBox7.getSelectedItem();
					int lake= (Integer) comboBox8.getSelectedItem();
					int river= (Integer) comboBox9.getSelectedItem();
					int bar= (Integer) comboBox10.getSelectedItem();
					
					newTraveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
					double lon = Double.valueOf(lonTf.getText()).doubleValue();
					double lat = Double.valueOf(latTf.getText()).doubleValue();
					newTraveller.setGeodesic_vector(new double[] {lon,lat});
					travellers.add(newTraveller);
					try {
						tester.writeJSON(travellers);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if(age>60 && age<=115) {
					ElderTraveller newTraveller = new ElderTraveller();
					String name = nameTf.getText();
					int phone = Integer.valueOf(phoneTf.getText()).intValue();
					newTraveller.setName(name);
					newTraveller.setPhone(phone);
					newTraveller.setTimestamp(date.getTime());
					
					int cafe = (Integer) comboBox1.getSelectedItem();
					int sea = (Integer) comboBox2.getSelectedItem();
					int museum = (Integer) comboBox3.getSelectedItem();
					int restaurant= (Integer) comboBox4.getSelectedItem();
					int stadium= (Integer) comboBox5.getSelectedItem();
					int cinema= (Integer) comboBox6.getSelectedItem();
					int mountain= (Integer) comboBox7.getSelectedItem();
					int lake= (Integer) comboBox8.getSelectedItem();
					int river= (Integer) comboBox9.getSelectedItem();
					int bar= (Integer) comboBox10.getSelectedItem();
					
					newTraveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
					double lon = Double.valueOf(lonTf.getText()).doubleValue();
					double lat = Double.valueOf(latTf.getText()).doubleValue();
					newTraveller.setGeodesic_vector(new double[] {lon,lat});
					travellers.add(newTraveller);
					try {
						tester.writeJSON(travellers);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 				
			}
		});
		
	}
	
	public static void itemTabPanel2(ArrayList<Traveller> noDupsTrav,ArrayList<City> cities) throws JsonParseException, JsonMappingException, IOException, SQLException {
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2,2));

		JLabel nameLabel = new JLabel("Select Traveller: ");
		panel2.add(nameLabel);

		JComboBox<Traveller> testCombo = new JComboBox<Traveller>();
		testCombo.setModel(new DefaultComboBoxModel<Traveller>(noDupsTrav.toArray(new Traveller[0])));
		panel2.add(testCombo);
		
		JLabel cityLabel = new JLabel("Select City: ");
		panel2.add(cityLabel);
		
		JComboBox<City> cityCombo = new JComboBox<City>();
		cityCombo.setModel(new DefaultComboBoxModel<City>(cities.toArray(new City[0])));
		panel2.add(cityCombo);
		
	}
	
}
