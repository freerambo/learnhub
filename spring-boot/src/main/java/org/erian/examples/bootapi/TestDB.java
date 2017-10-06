/*
 * Copyright: Energy Research Institute @ NTU
 * weather-api
 * org.erian.examples.bootapi -> TestDB.java
 * Created on 3 Apr 2017-3:06:08 pm
 */
package org.erian.examples.bootapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  3 Apr 2017 3:06:08 pm
 */
public class TestDB {

	
	public static void main(String[] args) throws SQLException, ClassNotFoundException 
	{
	    Class.forName("com.mysql.jdbc.Driver");

	    Connection conn = DriverManager.getConnection("jdbc:mysql://155.69.218.214:3306/weather_station?characterEncoding=UTF-8", "weather", "Zhu-YUan?Bo");

	    Statement stmt = conn.createStatement();

	    String strSelect = "SELECT * FROM `weather_station`.ISSData limit 10";
	    ResultSet rset = stmt.executeQuery(strSelect);

	    while (rset.next()) {   
	        String f = rset.getString("DewPoint");
	        System.out.println(f);
	    }
	}
}
