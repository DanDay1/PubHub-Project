package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;

/**
 * Interface for our Data Access Object to handle database queries related to Tag.
 */
public interface TagDAO {

	public List<Tag> getAllTags();
	public boolean addTag(String tagName, String isbn);
	public boolean removeTag(String tagName, String isbn);
	public List<Tag> retrieveBookTags(String bookName);
	public List<Book> getBooksByGivenTag(String tagName);
	
}
	//A method to add a tag to a book, given the tag name and a reference to a book (either a Book reference variable or just an ISBN-13)
	//A method to remove a tag from a book, given the tag name and a reference to a book (either a Book reference variable or just an ISBN-13)
	//A method to retrieve all tags that have been added to a given book
	//A method to retrieve all books that have a given tag. Hint: This will require either a SQL JOIN statement or a nested query.