<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="gamesquery.VideoGame" %>   
<%@page import="org.apache.jena.query.*" %>
<%@page import="org.apache.jasper.servlet.JspServlet" %>
<%@page import="java.io.File"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RESULTS</title>
</head>
<% 
//get genre and publisher of game from previous jsp file
String genre=request.getParameter("genre");
String publisher=request.getParameter("publisher");
//call VideoGame class for query
VideoGame vd=new VideoGame(genre,publisher);
%>

<body>

<form name="form2" action="select_table.jsp" method="post"> 
 <input type="submit" value="See Results" name="submit"/> 
</form>

</body>
</html>