package com.app.dao.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDBConnection {
	private static Connection connection;

	private MySqlDBConnection() {
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/ecom_console_db";
		String username = "root";
		String password = "Iwtl@123";
		connection = DriverManager.getConnection(url, username, password);
		return connection;

	}
}
