<%@ page import="books.DB" %>

<form name="input" method="post" action="displayBooks.jsp">
	<br>This is a simple book search engine to be able find the books by an author.<br/>
    <br>You are doing your operations as user <%out.println(session.getAttribute("user"));%>.<br/>
	 *Author(Required): <br><input type="text" name="author"/> <br/>
	 Book Name(Optional):<br><input type="text" name="book"/> <br/>
     Genre(Optional):<br> <input type="text" name="genre"/> <br/>
     Series(Optional):<br> <input type="text" name="series"/> <br/>
     
    <input type="submit" value="Submit" /> <br>
    
 
    <br>Developed by Guneykan Ozgul for the course CmpE 352.<br/>
    
    
    
</form>
			
         <input type="button" value="See your saved books" onclick="window.location.href='seeData.jsp';"/>
         <input type="button" value="Change user name" onclick="window.location.href='Home.htm';"/>


