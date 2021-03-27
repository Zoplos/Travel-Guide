package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

public class City implements Comparable<City>{
	
	private String name;
	private String country;
	private int terms_vector[];
	private double geodesic_vector[];
	private double similarity;
	
	public City(String name, String country, int[] terms_vector, double[] geodesic_vector) {
		super();
		this.name = name;
		this.country = country;
		this.terms_vector = terms_vector;
		this.geodesic_vector = geodesic_vector;		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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

	public void RetrieveData(String appid) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q="+name+","+country+"&APPID="+appid+""), OpenWeatherMap.class);		
		
		System.out.println(name+" lat: " + weather_obj.getCoord().getLat()+" lon: " + weather_obj.getCoord().getLon()); //Για να ελέγξω την ορθότητα των δεδομένων lat & lon
		
		MediaWiki mediaWiki_obj =  mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+name+"&format=json&formatversion=2"),MediaWiki.class);
		
		String article = mediaWiki_obj.getQuery().getPages().get(0).getExtract(); 
		
		geodesic_vector[0] = weather_obj.getCoord().getLat();         //Γέμισμα πίνακα με coordinates της πόλης
		geodesic_vector[1] = weather_obj.getCoord().getLon();
		
		terms_vector[0] = countCriterionfCity(article, "cafe");       //Γέμισμα πίνακα με τον int που αναλογεί στο πόσες φορές   
		terms_vector[1] = countCriterionfCity(article, "sea");        //  βρέθηκε το criterion
		terms_vector[2] = countCriterionfCity(article, "museum");
		terms_vector[3] = countCriterionfCity(article, "restaurant");
		terms_vector[4] = countCriterionfCity(article, "stadium");
		terms_vector[5] = countCriterionfCity(article, "cinema");
		terms_vector[6] = countCriterionfCity(article, "mountain");
		terms_vector[7] = countCriterionfCity(article, "lake");
		terms_vector[8] = countCriterionfCity(article, "river");
		terms_vector[9] = countCriterionfCity(article, "bar");
	}
	
	public static int countCriterionfCity(String cityArticle, String criterion) {
		cityArticle=cityArticle.toLowerCase();
		int index = cityArticle.indexOf(criterion);
		int count = 0;
		while (index != -1) {
		    count++;
		    cityArticle = cityArticle.substring(index + 1);
		    index = cityArticle.indexOf(criterion);
		}
		return count;
	}

	/*public static Comparator<City> CitySimComparator = new Comparator<City>() {

		@Override
		public int compare(City c1, City c2) {
			double citySim1 = c1.getSimilarity();
			double citySim2 = c2.getSimilarity();
			return Double.compare(citySim2, citySim1);
		}
		
	};*/

	@Override
	public int compareTo(City arg0) {
		if(this.similarity>arg0.similarity) {
			return -1;
		} else if (this.similarity<arg0.similarity) {
			return 1;
		} else {
			return 0;
		}		
	}
	
	
	
}
