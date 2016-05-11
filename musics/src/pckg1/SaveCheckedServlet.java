package pckg1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SaveCheckedServlet")
public class SaveCheckedServlet {
	private static final long serialVersionUID = 1L;

	public SaveCheckedServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter pw = response.getWriter();

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Singles</title>");
		pw.println("</head>");
		pw.println("<body>");

		pw.println("<form method=\"post\" action =\"MainServlet\" >");

		pw.println("<b>Search Singles</b>");

		pw.println("<input  type=\"text\" name=\"keyword\" >");
		pw.println("<input  type=\"submit\" value=\"Search\">");

		pw.println("</form>");

		pw.println(MainServlet.outputtable);
		
		pw.println("</body>");
		pw.println("</html>");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");

		PrintWriter pw = response.getWriter();

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Singles</title>");
		pw.println("</head>");
		pw.println("<body>");

		pw.println("<form method=\"post\" action =\"MainServlet\" >");

		pw.println("<b>Search Singles</b>");
		//pw.println("<b>"+request.getParameter("box")+"</b>");

		pw.println("<input  type=\"text\" name=\"keyword\" >");
		pw.println("<input  type=\"submit\" value=\"Search\">");

		pw.println("</form>");

		pw.println(MainServlet.outputtable);
		
		pw.println("</body>");
		pw.println("</html>");
		// ----------------------------------------------------

//		try {
//			functions.connect();
//			functions.resetmark();
//		} catch (ClassNotFoundException | SQLException e) {	e.printStackTrace();}
//
//		// ----------------------------------------------------
//
//		String values = request.getParameter("box1");
	}



}
