package DB2022TEAM03;

import java.sql.*;
import javax.swing.*;

import DB2022TEAM03.GEUNJU.M_MainScreen;

import java.awt.*;

public class YeonWoo extends JFrame{

	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03"; 
		
	//Database user, password
	static final String  USER = "root";
	static final String PASSWORD="yeonw00";
		
	//Connection conn;
	public static Connection conn;
	
	
	public static void main(String[] args) {
		System.out.println("Connecting to database...");
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
			
			System.out.println("Login succeds");
		}
		catch(SQLException sqle) { 
			System.out.println("Error: " + sqle);
		}
		
		new StartScreen();
	}

}
