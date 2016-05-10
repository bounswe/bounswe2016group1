<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
	<c:forEach var="country" items="${countryList.countries}" varStatus="status">
		<p>${country}</p>
	</c:forEach>
</body>
</html>
