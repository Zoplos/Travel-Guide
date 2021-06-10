package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DBConnection {
	static Connection db_con_obj = null;
	static PreparedStatement db_prep_obj = null;
	
	protected static void makeJDBCConnection() {
		 
		try {//We check that the DB Driver is available in our project.		
			Class.forName("oracle.jdbc.driver.OracleDriver"); //This code line is to check that JDBC driver is available. Or else it will throw an exception. Check it with 2. 
			System.out.println("Congrats - Seems your oracle JDBC Driver Registered!"); 
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
			db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl","it21826","it21826");// Returns a connection to the URL.
			//Attempts to establish a connection to the given database URL. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers.
			if (db_con_obj != null) { 
				System.out.println("Connection Successful! Enjoy. Now it's time to CRUD data. ");
				
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Oracle Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}
	
	protected static HashMap<String, City> LoadData() throws SQLException {
		db_prep_obj = db_con_obj.prepareStatement("select * from cities");
		ResultSet  rs = db_prep_obj.executeQuery();
		HashMap<String,City> cities = new HashMap<String,City>();
		
		
	    while (rs.next()){
	    	
	    	String city_name = rs.getString("CITY_NAME");
	    	String country = rs.getString("COUNTRY");
	    	double lat = rs.getDouble("LAT");
	        double lon = rs.getDouble("LON");
	        int term_1 = rs.getInt("CAFE");
	        int term_2 = rs.getInt("SEA");
	        int term_3 = rs.getInt("MUSEUM");
	        int term_4 = rs.getInt("RESTAURANT");
	        int term_5 = rs.getInt("STADIUM");
	        int term_6 = rs.getInt("CINEMA");
	        int term_7 = rs.getInt("MOUNTAIN");
	        int term_8 = rs.getInt("LAKE");
	        int term_9 = rs.getInt("RIVER");
	        int term_10 = rs.getInt("BAR");
	        double geo[] = {lat,lon};
	    	int terms[]= {term_1,term_2,term_3,term_4,term_5,term_6,term_7,term_8,term_9,term_10};
	    	
	    	cities.put(city_name, new City(city_name,country,terms,geo));
//	        System.out.println("The data are: "+ city_name + " " + country + " "+ lat+ " "+ lon+ " "+ term_1+ " "+ term_2+ " "+ term_3+ " "+ term_4+ " "+ term_5
//	        		+ " "+ term_6+ " "+ term_7+ " "+ term_8+ " "+ term_9+ " "+ term_10);
	    }
	    return cities;
	}
	
	protected static void addDataToDB(City city) {
		 
		try {
			String insertQueryStatement = "INSERT  INTO  CITIES  VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";			
			db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
			db_prep_obj.setString(1, city.getName());//.setInt(1, newKey);//.setString
			db_prep_obj.setString(2, city.getCountry());
			db_prep_obj.setDouble(3, city.getGeodesic_vector()[0]);//.setInt(2, year);
			db_prep_obj.setDouble(4, city.getGeodesic_vector()[1]);
			db_prep_obj.setInt(5, city.getTerms_vector()[0]);
			db_prep_obj.setInt(6, city.getTerms_vector()[1]);
			db_prep_obj.setInt(7, city.getTerms_vector()[2]);
			db_prep_obj.setInt(8, city.getTerms_vector()[3]);
			db_prep_obj.setInt(9, city.getTerms_vector()[4]);
			db_prep_obj.setInt(10, city.getTerms_vector()[5]);
			db_prep_obj.setInt(11, city.getTerms_vector()[6]);
			db_prep_obj.setInt(12, city.getTerms_vector()[7]);
			db_prep_obj.setInt(13, city.getTerms_vector()[8]);
			db_prep_obj.setInt(14, city.getTerms_vector()[9]);			
			// execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
			int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
			System.out.println("Rows "+numRowChanged+" changed.");
			
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
	}
}
