package examples.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

import examples.pubhub.dao.TagDAO;
import examples.pubhub.dao.TagDAOImpl;

/**
 * Servlet implementation class AddTagServlet
 */
@WebServlet("/AddTag")
public class AddTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		System.out.println("AddTagServlet.java line 25");
		request.getRequestDispatcher("addTag.jsp").forward(request, response); 
		
		String isbn = request.getParameter("isbn13");
		System.out.println("AddTagServlet.java getParameter isbn13 "+ isbn);
		String tagName = request.getParameter("tagNames");
		System.out.println("AddTagServlet.java getParameter tagName "+ tagName);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		System.out.println("AddTagServlet.java line 40");
		String isbn = request.getParameter("isbn13");
		System.out.println("AddTagServlet.jaca getParameter isbn13 "+ isbn);
		String tagName = request.getParameter("tagName");
		System.out.println("AddTagServlet.java getParameter tagName "+ tagName);
	
		
		BookDAO bookdao = DAOUtilities.getBookDAO();
		
		TagDAO dao = DAOUtilities.getTagDAO();
		
		Book tempBook  = bookdao.getBookByISBN(isbn);
		String ErrorMessage = "There was a problem adding this tag";
		
		
		if(tempBook != null){

			
			isSuccess = dao.addTag(tagName, isbn);
		}else {
			
			isSuccess = false;
			ErrorMessage += ", couldn't find the book";
		}
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Tag successfully added");
			request.getSession().setAttribute("messageClass", "alert-success");
			System.out.println("AddTagServlet.java Servlet success");
			response.sendRedirect(request.getContextPath() + "/AddTag");
		}else {
			System.out.println("AddTagServlet.java Servlet Problem");
			request.getSession().setAttribute("message", ErrorMessage);
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("addTag.jsp").forward(request, response);
		}
	}

}