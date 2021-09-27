package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import examples.pubhub.dao.*;
import examples.pubhub.model.*;
import examples.pubhub.utilities.*;
@WebServlet("/RetrieveBookTags")
public class RetrieveBookTagsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/retrieveBookTags.jsp").forward(request, response);
		HttpSession session = request.getSession();
		session.invalidate(); 
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String book = request.getParameter("bookName");

		System.out.println("RetrieveBookTagsServlet tag name = " + book);

		TagDAO dao = DAOUtilities.getTagDAO();
		List<Tag> bookTags = dao.retrieveBookTags(book);

		ArrayList<String> bookString = new ArrayList<String>();
		
		for (int i = 0; i < bookTags.size(); i++) {
			System.out.println("RetrieveBookTagsServlet: tag name " + bookTags.get(i).getDescription());
		   bookString.add(bookTags.get(i).getDescription());
		}
		System.out.println("RetrieveBookTagsServlet: result count " + bookString.size());

		request.getSession().setAttribute("tags", bookTags);
		
		request.getSession().setAttribute("message", "Tags successfully retrieved");
		request.getSession().setAttribute("messageClass", "alert-success");
		request.getRequestDispatcher("ListBookTags.jsp").forward(request, response);
	}

}
