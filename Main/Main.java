package DB2022Team03.Main;

import java.sql.*;
import javax.swing.*;

public class Main extends JFrame {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/DB2022Team03";

	// Database user, password
	static final String USER = "DB2022Team03";
	static final String PASSWORD = "DB2022Team03";

	// Connection conn
	public static Connection conn;

	public static void main(String[] args) {
		System.out.println("Connecting to database...");

		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

			System.out.println("Login succeeds");
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle);
		}

		new StartScreen(conn);
	}

}
