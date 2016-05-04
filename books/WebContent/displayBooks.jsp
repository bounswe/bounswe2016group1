<%@ page import="books.Books" %>
<%@page import= "org.apache.jena.query.Query" %>
<%@page import="org.apache.jena.query.QueryExecution" %>
<%@page import="org.apache.jena.query.QueryExecutionFactory" %>
<%@page import="org.apache.jena.query.QueryFactory" %>
<%@page import="org.apache.jena.query.QuerySolution" %>
<%@page import="org.apache.jena.query.ResultSet" %>

<form action="writeData.jsp">
    
<%
out.println(Books.getData());
%>
    <input type="submit" value="Save" />


</form>
