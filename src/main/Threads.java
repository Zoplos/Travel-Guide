package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.WikipediaException;

class RetrieveThread implements Runnable {
	City city;
	HashMap<String, City> citiesHm;
	ArrayList<City> cities;
	
	public RetrieveThread(City c,HashMap<String,City> hm,ArrayList<City> cs) {
		this.city = c;
		this.citiesHm = hm;
		this.cities = cs;
	}
	@Override
	public void run() {
		try {
			city.RetrieveData();
		} catch (IOException | WikipediaException e) {
			e.printStackTrace();
		}
		citiesHm.put(city.getName(), city);
		cities.add(city);
		DBConnection.addDataToDB(city);
	}
}

class writeJsonThread implements Runnable{
	
	ArrayList<Traveller> travellers;
	
	public writeJsonThread(ArrayList<Traveller> t) {
		this.travellers = t;
	}
	
	@Override
	public void run() {
		try {
			App.tester.writeJSON(travellers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
