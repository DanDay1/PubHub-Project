package examples.pubhub.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.dao.TagDAOImpl;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "password";
	private static final String URL = "jdbc:postgresql://localhost:5432/PubHub";
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		//System.out.println("DAOUtilities: BBB DAOUtilities.java");
		System.out.println("DAOUtilities: AAA  DB connection");
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("DAOUtilities: Could not register driver!");
				e.printStackTrace();
			}
			System.out.println("DAOUtilities: AAA  Password connection");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("DAOUtilities: Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	public static BookDAO getBookDAO() {
		return new BookDAOImpl();
	}
	
	public static TagDAO getTagDAO() {
		return new TagDAOImpl();
	}

}
