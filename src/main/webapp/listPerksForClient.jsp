<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>perks for client</title>
</head>
<body>

<h1>"All Perks for Client"</h1>
<ul>
                <c:forEach var="perk" items="${perks}">
                <li>
                    <c:out value="${perk}"/>
                    <br></br>
                    </a>
                </li>
            </c:forEach>
    <br></br>
    <a href="/">Back</a>
</ul>
</body>
</html>



