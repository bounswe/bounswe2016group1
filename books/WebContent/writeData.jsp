<%@ page import="java.util.*" %>
<%@ page import="books.Books" %>

<% 

String select[]=request.getParameterValues("id");
Books.saveData(select);

%>
