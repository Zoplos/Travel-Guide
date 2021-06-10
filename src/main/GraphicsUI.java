package main;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class GraphicsUI {
	static JFrame f;  
	private static JPanel panel1;
	private static JPanel panel2;
	private static JPanel panel3;
	private static JPanel panel4;
	static Date date = new Date();
	
	protected static void GUI(ArrayList<Traveller> noDupsTrav,ArrayList<City> cities,ArrayList<Traveller> travellers,HashMap<String,City> citiesHm) throws JsonParseException, JsonMappingException, IOException, SQLException{  
	    f=new JFrame();  
	    itemTabPanel1(noDupsTrav); 
	    itemTabPanel2(noDupsTrav,cities,travellers);    
	    itemTabPanel3(noDupsTrav,cities,travellers,citiesHm);
	    itemTabPanel4(noDupsTrav);
	    
	    JTabbedPane tp=new JTabbedPane();
	    tp.setBounds(0,0,800,300);
	    tp.setBorder(null);
	    tp.add("Traveller Creation",panel1);  
	    tp.add("Visit",panel2);  
	    tp.add("Add City",panel3);
	    tp.add("Collaborative Filtering",panel4);
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
	

	writeJsonThread write = new writeJsonThread(travellers);
	addBtn.addMouseListener(new MouseAdapter(){			
		public void mouseClicked(MouseEvent e) {
			int age = Integer.valueOf(ageTf.getText()).intValue();
			if(age>=16 && age<=25) {
				YoungTraveller newTraveller = new YoungTraveller();
				
				String name = nameTf.getText();
				int phone = Integer.valueOf(phoneTf.getText()).intValue();
				
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
				
				double lon = Double.valueOf(lonTf.getText()).doubleValue();
				double lat = Double.valueOf(latTf.getText()).doubleValue();
				
				newTraveller.setName(name);
				newTraveller.setPhone(phone);
				newTraveller.setTimestamp(date.getTime());
				newTraveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
				newTraveller.setGeodesic_vector(new double[] {lon,lat});
				
				travellers.add(newTraveller);
				
				new Thread(write).start();
				
				addBtn.setText("Added Traveller!");
			} else if(age>25 && age <=60) {
				MiddleTraveller newTraveller = new MiddleTraveller();
				
				String name = nameTf.getText();
				int phone = Integer.valueOf(phoneTf.getText()).intValue();
				
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
				
				double lon = Double.valueOf(lonTf.getText()).doubleValue();
				double lat = Double.valueOf(latTf.getText()).doubleValue();
				
				newTraveller.setName(name);
				newTraveller.setPhone(phone);
				newTraveller.setTimestamp(date.getTime());
				newTraveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
				newTraveller.setGeodesic_vector(new double[] {lon,lat});
				
				travellers.add(newTraveller);
				
				new Thread(write).start();
				
				addBtn.setText("Added Traveller!");
			} else if(age>60 && age<=115) {
				ElderTraveller newTraveller = new ElderTraveller();
				
				String name = nameTf.getText();
				int phone = Integer.valueOf(phoneTf.getText()).intValue();
				
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
				
				double lon = Double.valueOf(lonTf.getText()).doubleValue();
				double lat = Double.valueOf(latTf.getText()).doubleValue();
				
				newTraveller.setName(name);
				newTraveller.setPhone(phone);
				newTraveller.setTimestamp(date.getTime());
				newTraveller.setTerms_vector(new int[] {cafe,sea,museum,restaurant,stadium,cinema,mountain,lake,river,bar});
				newTraveller.setGeodesic_vector(new double[] {lon,lat});
				
				travellers.add(newTraveller);
				
				new Thread(write).start();
				
				addBtn.setText("Added Traveller!");
			} 				
		}
		public void mouseExited(MouseEvent e) {
			addBtn.setText("Add Traveller");
		}
	});
	
}

	public static void itemTabPanel2(ArrayList<Traveller> noDupsTrav,ArrayList<City> cities,ArrayList<Traveller> travs) throws JsonParseException, JsonMappingException, IOException, SQLException {
	panel2 = new JPanel();
	panel2.setLayout(new GridLayout(2,4));

	JLabel nameLabel = new JLabel("Select Traveller: ");
	panel2.add(nameLabel);
			
	JComboBox<Traveller> travCombo = new JComboBox<Traveller>();
	travCombo.setModel(new DefaultComboBoxModel<Traveller>(noDupsTrav.toArray(new Traveller[0])));
	panel2.add(travCombo);
	
	JLabel pLabel = new JLabel("Enter p[0,1]: ");
	panel2.add(pLabel);
	
	JTextField pTf = new JTextField();
	panel2.add(pTf);
	
	JLabel cityLabel = new JLabel("Select up to five cities: ");
	panel2.add(cityLabel);
	
	JScrollPane scrollCity = new JScrollPane();
	JList<City> cityList = new JList<City>();
	cityList.setModel(new DefaultComboBoxModel<City>(cities.toArray(new City[0])));
	scrollCity.setViewportView(cityList);
	panel2.add(scrollCity);
	
	JButton cityButton = new JButton("Compare Cities");
	panel2.add(cityButton);
	
	JLabel result = new JLabel("Which city should you visit?");
	panel2.add(result);
	
	cityButton.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			Traveller selectedTraveller = (Traveller) travCombo.getSelectedItem();
			ArrayList<City> selectedCities = new ArrayList<City>();
			selectedCities = (ArrayList<City>) cityList.getSelectedValuesList();
			double p = Double.valueOf(pTf.getText()).doubleValue();
			
			selectedTraveller.setVisit(selectedTraveller.compare_cities(selectedCities, p));
			result.setText("You should visit:" + selectedTraveller.compare_cities(selectedCities, p));
			writeJsonThread write = new writeJsonThread(travs);
			new Thread(write).start();
			
		}
	});
	
}
	public static void itemTabPanel3(ArrayList<Traveller> noDupsTrav,ArrayList<City> cities,ArrayList<Traveller> travellers,HashMap<String,City> citiesHm) {
	panel3 = new JPanel();
	panel3.setLayout(new GridLayout(4,2));
	
	JLabel nameLabel = new JLabel("Select Traveller: ");
	panel3.add(nameLabel);
			
	JComboBox<Traveller> travCombo = new JComboBox<Traveller>();
	travCombo.setModel(new DefaultComboBoxModel<Traveller>(noDupsTrav.toArray(new Traveller[0])));
	panel3.add(travCombo);
	
//	JLabel fill1 = new JLabel(" ");
//	panel3.add(fill1);
//	JLabel fill2 = new JLabel(" ");
//	panel3.add(fill2);
	
	JLabel cityNameLabel = new JLabel("City name: ");
	panel3.add(cityNameLabel);
	
	JTextField cityNameTf = new JTextField();
	panel3.add(cityNameTf);
	
	JLabel cityCountryLabel = new JLabel("Country: ");
	panel3.add(cityCountryLabel);
	
	JTextField cityCountryTf = new JTextField("as 'gr'");
	panel3.add(cityCountryTf);
	
	JButton searchButton = new JButton("Search for city");
	panel3.add(searchButton);
	
//	JLabel fill3 = new JLabel(" ");
//	panel3.add(fill3);
//	JLabel fill4 = new JLabel(" ");
//	panel3.add(fill4);
	
	JLabel result = new JLabel("Waiting for results..");
	panel3.add(result);
	writeJsonThread write = new writeJsonThread(travellers);
	searchButton.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			String cityName = cityNameTf.getText();
			String cityCountry = cityCountryTf.getText();
			
			if(citiesHm.containsKey(cityName)) {
				result.setText("City: " + cityName + " is already in the Database!");
			} else {
				City city = new City(cityName, cityCountry,new int[10],new double[2]);
//				try {
//					city.RetrieveData();
//				} catch (IOException | WikipediaException e1) {
//					e1.printStackTrace();
//				}
				RetrieveThread retrieve = new RetrieveThread(city,citiesHm,cities);
				new Thread(retrieve).start();
//				citiesHm.put(cityName, city);
//				cities.add(city);
//				addDataToDB(city);
				result.setText("City: " + cityName + " added to DB!");
			}
			
			long timestamp =date.getTime();
			
			Traveller selectedTraveller = (Traveller) travCombo.getSelectedItem();
			if(selectedTraveller.getClass().getTypeName() == "main.YoungTraveller") {
				YoungTraveller newTrav = new YoungTraveller();
				newTrav = (YoungTraveller) App.copyTraveller(selectedTraveller,newTrav,timestamp);
				travellers.add(newTrav);
			} else if(selectedTraveller.getClass().getTypeName() == "main.MiddleTraveller") {
				MiddleTraveller newTrav = new MiddleTraveller();
				newTrav = (MiddleTraveller) App.copyTraveller(selectedTraveller,newTrav,timestamp);
				travellers.add(newTrav);
			} else if(selectedTraveller.getClass().getTypeName() == "main.ElderTraveller") {
				ElderTraveller newTrav = new ElderTraveller();
				newTrav = (ElderTraveller) App.copyTraveller(selectedTraveller,newTrav,timestamp);
				travellers.add(newTrav);
			}
			new Thread(write).start();
		}
	});
}
	public static void itemTabPanel4(ArrayList<Traveller> inputTravellers) {
	panel4 = new JPanel();
	panel4.setLayout(new GridLayout(3,2));
	JLabel nameLabel = new JLabel("Select Traveller: ");
	panel4.add(nameLabel);
			
	JComboBox<Traveller> travCombo = new JComboBox<Traveller>();
	travCombo.setModel(new DefaultComboBoxModel<Traveller>(inputTravellers.toArray(new Traveller[0])));
	panel4.add(travCombo);
	
	JLabel travLabel = new JLabel("Select Traveller: ");
	panel4.add(travLabel);
	
	JScrollPane scrollTrav = new JScrollPane();
	JList<Traveller> travList = new JList<Traveller>();
	travList.setModel(new DefaultComboBoxModel<Traveller>(inputTravellers.toArray(new Traveller[0])));
	scrollTrav.setViewportView(travList);
	panel4.add(scrollTrav);
	
	JButton filterButton = new JButton("Filter results: ");
	panel4.add(filterButton);
	
	JLabel result = new JLabel("");
	panel4.add(result);
	
	filterButton.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			Traveller selectedTraveller = (Traveller) travCombo.getSelectedItem();
			ArrayList<Traveller> selectedTravs = new ArrayList<Traveller>();
			selectedTravs = (ArrayList<Traveller>) travList.getSelectedValuesList();
			
			Optional<RecommendedCity> recommendedCity = 
					selectedTravs.stream().map(i->new RecommendedCity(i.getVisit(),App.innerDot(i.getTerms_vector(),selectedTraveller.getTerms_vector()))).max(Comparator.comparingInt(RecommendedCity::getRank));
			
			result.setText("You should visit:" + recommendedCity.get().getName());
			
		}
	});
	}
}
