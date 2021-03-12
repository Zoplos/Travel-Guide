package main;

public abstract class Traveller {
	
	private String name;
	private int phone;
	private int terms_vector[];
	private double geodesic_vector[];
	
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
	
	public void fillWithData(int[] terms_vector, double[] geodesic_vector){
		geodesic_vector[0] = 37.9795;
		geodesic_vector[1] = 23.7162;
		
		terms_vector[0] = 1;
		terms_vector[1] = 2;
		terms_vector[2] = 3;
		terms_vector[3] = 4;
		terms_vector[4] = 5;
		terms_vector[5] = 6;
		terms_vector[6] = 12;
		terms_vector[7] = 6;
		terms_vector[8] = 23;
		terms_vector[9] = 5;
	}
	
	private void Calculate_similarity( ) {
				
	}
	
}
