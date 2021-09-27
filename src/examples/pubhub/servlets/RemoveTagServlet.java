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
@WebServlet("/RemoveTag")
public class RemoveTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		System.out.println("RemoveTagServlet.java line 25");
		request.getRequestDispatcher("removeTag.jsp").forward(request, response); 
		
		String isbn = request.getParameter("isbn13");
		System.out.println("RemoveTagServlet.java getParameter isbn13 "+ isbn);
		String tagName = request.getParameter("tagName");
		System.out.println("RemoveTagServlet.java getParameter tagName "+ tagName);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		System.out.println("RemoveTagServlet.java line 40");
		String isbn = request.getParameter("isbn13");
		System.out.println("RemoveTagServlet.java getParameter isbn13 "+ isbn);
		String tagName = request.getParameter("tagName");
		System.out.println("RemoveTagServlet.java getParameter tagName "+ tagName);

		
        TagDAO dao = DAOUtilities.getTagDAO();

		String ErrorMessage = "Unable to find this tag";
		
		
			
			isSuccess = dao.removeTag(tagName, isbn);
			if(isSuccess)
			   System.out.println("RemoveTagServlet: success");
			
		else {
			
			isSuccess = false;
			ErrorMessage += ", couldn't remove the tag";
			System.out.println("RemoveTagServlet: Failed " + ErrorMessage);
		}
		
		if(isSuccess){
			request.getSession().setAttribute("message", "Tag successfully removed");
			request.getSession().setAttribute("messageClass", "alert-success");
			System.out.println("RemoveTagServlet.java Servlet success");
			response.sendRedirect(request.getContextPath() + "/RemoveTag");
		}else {
			System.out.println("RemoveTagServlet.java Servlet Problem");
			request.getSession().setAttribute("message", ErrorMessage);
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("removeTag.jsp").forward(request, response);
		}
	}

}