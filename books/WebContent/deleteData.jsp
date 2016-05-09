<form action="input.jsp">
<%@ page import="books.DB" %>

<%
DB.deleteAll((String)session.getAttribute("user"));

%>
    <input type="submit" value="Back" />


</form>