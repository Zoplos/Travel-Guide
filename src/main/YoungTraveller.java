package main;

public class YoungTraveller extends Traveller{

	public YoungTraveller(/*String name, int phone, int[] terms_vector, double[] geodesic_vector, long timestamp*/) {
		super(/*name, phone, terms_vector, geodesic_vector, timestamp*/);
		// TODO Auto-generated constructor stub
	}		
	
	public double calculate_similarity(City city,Traveller traveller,double p) {
		double result = p * similarity_terms_vector(traveller,city) + (1-p) * traveller.similarity_geodesic_vector(traveller, city);
		return result;
	}	
	
	private double similarity_terms_vector(Traveller traveller, City city) {
		double x=0;
		
		for(int i=0;i<city.getTerms_vector().length;i++) {
			x += Math.pow(traveller.getTerms_vector()[i] + city.getTerms_vector()[i],2);
		}
		
		double result = 1/(1 + Math.sqrt(x));
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
