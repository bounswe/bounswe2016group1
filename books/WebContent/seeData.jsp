<form action="input.jsp">
<%@ page import="books.DB" %>

<%

out.println(DB.printAll((String)session.getAttribute("user")));
%>
    <input type="submit" value="Back" />
         <input type="button" value="Clear all books" onclick="window.location.href='deleteData.jsp';"/>


</form>