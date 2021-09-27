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
@WebServlet("/GetBooksByGivenTag")
public class GetBooksByGivenTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/getBooksByGivenTag.jsp").forward(request, response);
		HttpSession session = request.getSession();
		session.invalidate(); 		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String tag = request.getParameter("tagName");

		System.out.println("GetBooksByGivenTagServlet tag name = " + tag);

		TagDAO dao = DAOUtilities.getTagDAO();
		List<Book> taggedBooks = dao.getBooksByGivenTag(tag);

		ArrayList<String> bookString = new ArrayList<String>();
		
		for (int i = 0; i < taggedBooks.size(); i++) {
			System.out.println("GetBooksByGivenTagServlet: book title " + taggedBooks.get(i).getTitle());
		   bookString.add(taggedBooks.get(i).getTitle());
		}
		System.out.println("GetBooksByGivenTagServlet: result count " + bookString.size());

		request.getSession().setAttribute("books", taggedBooks);
		
		request.getSession().setAttribute("message", "Books successfully retrieved");
		request.getSession().setAttribute("messageClass", "alert-success");
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}

}