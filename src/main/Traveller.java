package main;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Traveller implements Comparable<Traveller>{
	
	private String name;
	private int phone;
	private int terms_vector[];
	private double geodesic_vector[];
	protected double similarity;
	
	public Traveller(String name, int phone, int[] terms_vector, double[] geodesic_vector) {
		super();
		this.name = name;
		this.phone = phone;
		this.terms_vector = terms_vector;
		this.geodesic_vector = geodesic_vector;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int[] getTerms_vector() {
		return terms_vector;
	}
	public void setTerms_vector(int[] terms_vector) {
		this.terms_vector = terms_vector;
	}
	public double[] getGeodesic_vector() {
		return geodesic_vector;
	}
	public void setGeodesic_vector(double[] geodesic_vector) {
		this.geodesic_vector = geodesic_vector;
	}
		
	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public abstract double calculate_similarity(City city,Traveller traveller,double p);
	
	protected double similarity_geodesic_vector(Traveller traveller, City city) {
		double maxdist = 15_317;
		double x = distance(traveller.getGeodesic_vector()[0],traveller.getGeodesic_vector()[1],city.getGeodesic_vector()[0],city.getGeodesic_vector()[1]);
		double result = Math.log10(2/(2-(x/maxdist)))/Math.log10(2);
		return result;
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2) { 
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			
			dist = dist * 1.609344;  // Μετατροπή σε χιλιόμετρα
			
			return (dist);  //Γυρνάει κατευθείαν σε χιλιόμετρα
		}
	}		
	
	public City compare_cities(ArrayList<City> cities) {
		Collections.sort(cities);
		return cities.get(0);
	}
	
	public ArrayList<City> compare_cities(ArrayList<City> cities,int max){
		ArrayList<City> test = new ArrayList<City>();
		if(max<2 || max>5) {
			System.out.println("The integer given must be [2,5]");
			return null;
		} else {
			for(int i=1;i<max;i++) {
				test.add(cities.get(i));
			}
			return test;
		}				
	}
	
}
