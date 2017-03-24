package storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class for database connection
 *
 */
public enum Database {

	UNIQUEINSTANCE;
	private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";

	private Connection con;
	
	/**
	 * Private constructor for Singleton design pattern
	 */
	private Database() {
		con = makeConnection(DATABASE_PROPERTIES_FILENAME);
	}
	
	/**
	 * Getter for the connection field
	 * @return The connection object to the MS-SQL server
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * Sets up a connection according to the connection properties given in the properties file
	 * @param propertiesFilename Name of the file holding all the properties
	 * @return The connection object
	 */
	private Connection makeConnection(String propertiesFilename) {
		Properties properties = loadPropertiesFromFile(propertiesFilename);
		
		String driver = properties.getProperty("jdbc.driver");
		String url = properties.getProperty("jdbc.url");
		
		try {
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("driver not loaded!");
			e.printStackTrace();
			return con;
		}

		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println("no connection!");
			System.out.println(url);
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Load all properties from a file to a properties object
	 * @param propFile Name of the file holding all the properties
	 * @return An initialized properties object, based on the given file.
	 */
	private Properties loadPropertiesFromFile(String propFile) {
		Properties properties = new Properties();			
		InputStream propertyFile = null;
		
		try {
			propertyFile = new FileInputStream(propFile);
			properties.load(propertyFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (propertyFile != null) {
				try {
					propertyFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
}
