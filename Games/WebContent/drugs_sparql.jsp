<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DRUGS</title>
</head>

<body>

<h1>Video Games</h1>
	<form name="myForm" action="games_results.jsp" method="post"> 
	
		<table>
			<tbody>
				<tr>
					<td>Genre:</td>
					<td><input type="text" name="genre" value="" size="50"  /></td>
				</tr>

				
				<tr>
					<td>Publisher:</td>
					<td><input type="text" name="publisher" value="" size="50"  /></td>
				</tr>
			
			</tbody>
		
		</table>
<input type="reset" value="Clear" name="clear"/>
<input type="submit" value="Submit" name="submit"/>
	
	</form>
	
	
</body>

</html>