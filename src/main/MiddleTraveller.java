package main;


public class MiddleTraveller extends Traveller {

	public MiddleTraveller(/*String name, int phone, int[] terms_vector, double[] geodesic_vector, long timestamp*/) {
		super(/*name, phone, terms_vector, geodesic_vector, timestamp*/);
		// TODO Auto-generated constructor stub
	}
	
	public double calculate_similarity(City city,Traveller traveller,double p) {
		double result = p * similarity_terms_vector(traveller,city) + (1-p) * traveller.similarity_geodesic_vector(traveller, city);
		return result;
	}

	private double similarity_terms_vector(Traveller traveller, City city) {
		double x = 0; // x = σειρά αριθμητή
		double y = 0; // y = σειρα traveller terms vector
		double z = 0; // z = σειρά city terms_vector
		
		for(int i=0; i<city.getTerms_vector().length; i++) {
			x += traveller.getTerms_vector()[i] * city.getTerms_vector()[i];
			y += Math.pow(traveller.getTerms_vector()[i], 2); 
			z += Math.pow(city.getTerms_vector()[i], 2);
		}
		
		double result = x /(Math.sqrt(y) * Math.sqrt(z));
		return result;
	}
	
	@Override
	public int compareTo(Traveller arg0) {
		if(this.similarity>arg0.similarity) {
			return -1;
		} else if (this.similarity<arg0.similarity) {
			return 1;
		} else {
			return 0;
		}		
	}

	
}
