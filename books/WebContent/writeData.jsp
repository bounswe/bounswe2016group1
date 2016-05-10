<%@ page import="java.util.*" %>
<%@ page import="books.Books" %>
<%@ page import="java.sql.DriverManager" %> 


<% 

String select[]=request.getParameterValues("id");
out.println("Selected data is saved in a database named 'bookDB' and in a table 'book'");
out.println("Current data is as follows:");


out.println(Books.saveData(select,(String)session.getAttribute("user")));



%>
  <input type="button" value="Make another Search" onclick="window.location.href='input.jsp';"/>
