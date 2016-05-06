<%@ page import="books.Books" %>
<%@page import= "org.apache.jena.query.Query" %>
<%@page import="org.apache.jena.query.QueryExecution" %>
<%@page import="org.apache.jena.query.QueryExecutionFactory" %>
<%@page import="org.apache.jena.query.QueryFactory" %>
<%@page import="org.apache.jena.query.QuerySolution" %>
<%@page import="org.apache.jena.query.ResultSet" %>

<form action="writeData.jsp">
    
<%

String author=(String)request.getParameter("author");
String genre=(String)request.getParameter("genre");
String book=(String)request.getParameter("book");
System.out.println(author+genre);
out.println(Books.getData(author,genre,book));
%>
    <input type="submit" value="Save" />


</form>
