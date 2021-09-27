package examples.pubhub.model;

//import java.time.LocalDate;

public class Tag {

	private String isbn;			// International Standard Book Number, unique
	private String description;
	//private String title;
	//private String author;
	
	
	
	//private byte[] content;

	// Constructor used when no date is specified
	public Tag(String isbn, String description) {
		this.isbn = isbn;
		this.description = description;
	}
	
	
	// Default constructor
	public Tag() {
		this.isbn = null;
		this.description = null;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
