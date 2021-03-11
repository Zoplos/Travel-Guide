package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

public class City {
	
	//private String appid ="f6dcc229d7417350c66a4ebd183e128c";
	private String name;
	private String country;
	private int terms_vector[];
	private double geodesic_vector[];
	
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
	
	public void RetrieveData(String name, String country, String appid, int[] terms_vector, double[] geodesic_vector) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		OpenWeatherMap weather_obj = mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q="+name+","+country+"&APPID="+appid+""), OpenWeatherMap.class);
		//System.out.println(name+" temperature: " + (weather_obj.getMain()).getTemp());
		System.out.println(name+" lat: " + weather_obj.getCoord().getLat()+" lon: " + weather_obj.getCoord().getLon());
		MediaWiki mediaWiki_obj =  mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+name+"&format=json&formatversion=2"),MediaWiki.class);
		//System.out.println(name+" Wikipedia article: "+mediaWiki_obj.getQuery().getPages().get(0).getExtract());
		String article = mediaWiki_obj.getQuery().getPages().get(0).getExtract();
		
		geodesic_vector[0] = weather_obj.getCoord().getLat();
		geodesic_vector[1] = weather_obj.getCoord().getLon();
		
		terms_vector[0] = countCriterionfCity(article, "mountain");
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
	
}
