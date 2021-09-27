package examples.pubhub.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Tag;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Implementation for the TagDAO, responsible for querying the database for Tag objects.
 */
public class TagDAOImpl implements TagDAO {

	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	/*------------------------------------------------------------------------------------------------*/
	//GET ALL TAGS
	
	@Override
	public List<Tag> getAllTags() {
		System.out.println("TagDAOImpl Get All Tags");
		
		List<Tag> Tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM Book_Tags";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				// We need to populate a Tag object with info for each row from our query result
				Tag tag = new Tag();

				// Each variable in our Tag object maps to a column in a row from our results.
				tag.setIsbn(rs.getString("isbn_13"));
				tag.setDescription(rs.getString("tag_name"));

				
				// Finally we add it to the list of Book objects returned by this query.
				Tags.add(tag);
				System.out.println("testing the Sysprint");
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return Tags;
	}

	
	/*------------------------------------------------------------------------------------------------*/
	// ADD TAGS
	@Override
	public boolean addTag(String tagName, String isbn) {
		System.out.println("TagDAOImpl Add Tag");
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO Book_Tags VALUES (?,?)"; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			//
			
			stmt.setString(1, tagName);
			stmt.setString(2, isbn);
			System.out.println("TagDAOImpl Add Tag" + tagName);
			System.out.println("TagDAOImpl Add Tag" + isbn);
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed

			if (stmt.executeUpdate() != 0)
				return true; 
			else 
				return false; 
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	
	/*------------------------------------------------------------------------------------------------*/
	// REMOVE TAG
	@Override
	
	public boolean removeTag(String tag_name, String isbn_13) {
		System.out.println("TagDAOImpl Remove Tag");

		boolean returnValue = false;

		try {
				connection = DAOUtilities.getConnection();
				String selectSql = "SELECT FROM Book_Tags WHERE tag_name = ? AND isbn_13 = ?";
				stmt = connection.prepareStatement(selectSql);
				

				stmt.setString(1, tag_name);
				stmt.setString(2, isbn_13);
				System.out.println("TagDAOImpl selectSQL" + tag_name);
				System.out.println("TagDAOImpl selectSQL" + isbn_13);
				
				ResultSet rs = stmt.executeQuery();
				System.out.println("TagDAOImpl line 129");
				while (rs.next()) {
					System.out.println("TagDAOImpl line 132");
					String sql = "DELETE FROM Book_Tags WHERE tag_name = ? AND isbn_13 = ?";
					stmt = connection.prepareStatement(sql);
					
					stmt.setString(1, tag_name);
					stmt.setString(2, isbn_13);
					System.out.println("TagDAOImpl Remove Tag" + tag_name);
					System.out.println("TagDAOImpl Remove Tag" + isbn_13);
				
				
					
					if (stmt.executeUpdate() != 0)
						returnValue = true;
					else
						returnValue = false;
			}
				

				
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}

	
	return returnValue; 
	
	}

	
	/*------------------------------------------------------------------------------------------------*/
	// LIST ALL TAGS ADDED TO BOOK
		@Override
	public List<Tag> retrieveBookTags(String bookName){
		System.out.println("Retrieve Book Tags");
		List<Tag> tags = new ArrayList<>();
		System.out.println("TagDAOImpl bookName1 = " + bookName);
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "SELECT *\n"
					+ "			FROM book_tags\n"
					+ "			INNER JOIN books\n"
					+ "			ON\n"
					+ "			    books.isbn_13 = book_tags.isbn_13"
					+ "			WHERE\n"
					+ "			  books.title = ?" 
					;
			
			System.out.println("TagDAOImpl bookName2 = " + bookName);
			System.out.println("TagDAOImpl sql = " + sql);
			
			
			  
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, bookName);

			
			
			ResultSet rs = stmt.executeQuery();
			

			while (rs.next()) {
				Tag tag = new Tag();
				
				String xx = rs.getString("tag_name");
				tag.setDescription(xx);
				tags.add(tag);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
	}	
	
	/*------------------------------------------------------------------------------------------------*/
	//LIST ALL BOOKS BY TAG
	
	@Override
	public List<Book> getBooksByGivenTag(String tagName){
		System.out.println("TagDAOImpl List All Books by Tag");
		List<Book> books = new ArrayList<>();
		System.out.println("TagDAOImpl tagName1 = " + tagName);
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "SELECT *\n"
					+ "			FROM books\n"
					+ "			INNER JOIN book_tags\n"
					+ "			ON\n"
					+ "			    book_tags.isbn_13 = books.isbn_13"
					+ "			WHERE\n"
					+ "			  book_tags.tag_name = ?" 
					;
			
			System.out.println("TagDAOImpl tagName2 = " + tagName);
			System.out.println("TagDAOImpl sql = " + sql);
			
			
			  
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tagName);

			
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();

				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return books;
	}	
	
	/*------------------------------------------------------------------------------------------------*/

	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		System.out.println("TagDAOImpl Close Resources");

		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}