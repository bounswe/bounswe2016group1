<%@ page import="books.Books" %>
<%@ page import="books.DB" %>
<%@page import= "org.apache.jena.query.Query" %>
<%@page import="org.apache.jena.query.QueryExecution" %>
<%@page import="org.apache.jena.query.QueryExecutionFactory" %>
<%@page import="org.apache.jena.query.QueryFactory" %>
<%@page import="org.apache.jena.query.QuerySolution" %>
<%@page import="org.apache.jena.query.ResultSet" %>

<form action="seeData.htm">
    
<%

String user=(String)request.getParameter("user");
session.setAttribute("user",user); 
String redirectURL = "input.jsp"; 
response.sendRedirect(redirectURL); 
%>


</form>
