package main;

public class YoungTraveller extends Traveller{

	public YoungTraveller(String name, int phone, int[] terms_vector, double[] geodesic_vector) {
		super(name, phone, terms_vector, geodesic_vector);
		// TODO Auto-generated constructor stub
	}		
	
	public double similarity_terms_vector(YoungTraveller traveller, City city) {
		double x=0;
		
		for(int i=0;i<city.getTerms_vector().length;i++) {
			x += Math.pow(traveller.getTerms_vector()[i] + city.getTerms_vector()[i],2);
		}
		
		double result = 1/(1 + Math.sqrt(x));
		
		return result;
	}
	
}
