<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>apartments</title>
</head>
<body>

<h1>"All Apartments"</h1>
<ul>
                <c:forEach var="apartment" items="${apartments}">
                <li>
                <a href='<c:url value="/Controller?apartment_id=${apartment.id}&command=apartment_id"/>'>
                    <c:out value="${apartment}"/>
                    <br></br>
                </a>
                </li>
            </c:forEach>
    <br></br>
    <a href="/">Back</a>
</ul>
</body>
</html>



