package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection con = null;
	
	public static Connection getConnection() {
		if (con == null) {
			try {
				Properties pro = loadProperties();
				String url = pro.getProperty("dburl");
				con = DriverManager.getConnection(url, pro);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return con;
	}
	
	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				throw new db.DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fis = new FileInputStream("db.properties")) {
			Properties pro = new Properties();
			pro.load(fis);
			return pro;
			
		} 
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatment(Statement sta) {
		if (sta != null) {
			try {
				sta.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet res) {
		if (res != null) {
			try {
				res.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
