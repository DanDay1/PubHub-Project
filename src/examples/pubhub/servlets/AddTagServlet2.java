package examples.pubhub.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.dao.TagDAOImpl;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

@MultipartConfig // This annotation tells the server that this servlet has
					// complex data other than forms
// Notice the lack of the @WebServlet annotation? This servlet is mapped the old
// fashioned way - Check the web.xml!
public class AddTagServlet2 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		System.out.println("AddTagServlet line 31");
		request.getRequestDispatcher("addTag.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("AddTagServlet  AddTagServlet.java");

		String tagName = req.getParameter("tagName");
		System.out.println("AddTagServlet  parameter is tagName");
		String isbn13 = req.getParameter("isbn13");
		System.out.println("AddTagServlet  parameter is isbn13");

		TagDAOImpl tg = new TagDAOImpl();
		boolean isSuccess = tg.addTag(tagName, isbn13);
		System.out.println("AddTagServlet  After Add Tag");

			System.out.println("AddTagServlet tag added");
			
			// ASSERT: book with isbn already exists

/*
			Book book = new Book();
			book.setIsbn13(req.getParameter("isbn13"));
			book.setTitle(req.getParameter("title"));
			book.setAuthor(req.getParameter("author"));
			book.setPublishDate(LocalDate.now());
			book.setPrice(Double.parseDouble(req.getParameter("price")));
*/
			// Uploading a file requires the data to be sent in "parts", because
			// one HTTP packet might not be big
			// enough anymore for all of the data. Here we get the part that has
			// the file data
			/*Part content = req.getPart("content");

			InputStream is = null;
			ByteArrayOutputStream os = null;

			try {
				is = content.getInputStream();
				os = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];

				while (is.read(buffer) != -1) {
					os.write(buffer);
				}
				
				book.setContent(os.toByteArray());

			} catch (IOException e) {
				System.out.println("Could not upload file!");
				e.printStackTrace();
			} finally {
				if (is != null)
					is.close();
				if (os != null)
					os.close();
			}
*/
			//boolean isSuccess = dao.addTag(tagName);
			
			if(isSuccess){
				req.getSession().setAttribute("message", "Tag successfully added");
				req.getSession().setAttribute("messageClass", "alert-success");
				System.out.println("CCC Servlet success");
			

				// We use a redirect here instead of a forward, because we don't
				// want request data to be saved. Otherwise, when
				// a user clicks "refresh", their browser would send the data
				// again!
				// This would be bad data management, and it
				// could result in duplicate rows in a database.
				
				//Still trying to figure out what to do with line 104
				resp.sendRedirect(req.getContextPath() + "/BookPublishing");
			}else {
				System.out.println("CCC Servlet Problem");
				req.getSession().setAttribute("message", "There was a problem adding this tag");
				req.getSession().setAttribute("messageClass", "alert-danger");
				req.getRequestDispatcher("addTag.jsp").forward(req, resp);
				
			}
		}
	}


