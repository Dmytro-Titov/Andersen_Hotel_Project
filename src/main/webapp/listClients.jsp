<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>clients</title>
</head>
<body>

<h1>"All Clients"</h1>
<ul>
                <c:forEach var="client" items="${clients}">
                <li>
                <a href='<c:url value="/Controller?client_id=${client.id}&command=client_id"/>'>
                <c:out value="${client}"/>
                <br></br>
                </a>
                </li>
            </c:forEach>
    <br></br>
    <a href="/">Back</a>
</ul>
</body>
</html>



