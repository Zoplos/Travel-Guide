package main;

public class ElderTraveller extends Traveller{

	public ElderTraveller(/*String name, int phone, int[] terms_vector, double[] geodesic_vector, long timestamp*/) {
		super(/*name, phone, terms_vector, geodesic_vector, timestamp*/);
		// TODO Auto-generated constructor stub
	}

	public double calculate_similarity(City city,Traveller traveller,double p) {
		double result = p * similarity_terms_vector(traveller,city) + (1-p) * traveller.similarity_geodesic_vector(traveller, city);
		return result;
	}
	
	private double similarity_terms_vector(Traveller traveller, City city) {
		double sum_intersection = 0;
		double sum_union = 0;
		for(int i=0; i<traveller.getTerms_vector().length;i++) {
			if(traveller.getTerms_vector()[i]>=1 && city.getTerms_vector()[i]>=1){
				sum_intersection+=1;
			}
			if(traveller.getTerms_vector()[i]>=1 || city.getTerms_vector()[i]>=1){
				sum_union+=1;
			}
		}
		if(sum_union == 0) {
			return 0;
		}
		return sum_intersection/sum_union;
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
