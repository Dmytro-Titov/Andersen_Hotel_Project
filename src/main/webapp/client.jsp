<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>post</title>
</head>
<body>
<h1>"Client"</h1>
<ul>
    <p>"${client_id}"</p>
</ul>

<h1>"Check In"</h1>
<h3>"Insert duration and apartment id"</h3>
<form action="Controller?command=check_in&client=${client_id}" method="post">
<br>
<label>Duration</label>
<br>
<input name="duration" type="string"/>
<br>
<br>
<label>Apartment ID</label>
<br>
<input name="apartment_id" type="string"/>
<br>
<br>
<input type="submit" value="check in"/>
</form>
<br>
<br>
<br>

<h1>"Check Out"</h1>
<h3>"Confirm Check out"</h3>
<form action="Controller?command=check_out&client=${client_id}" method="post">
<br>
<input type="submit" value="check out"/>
</form>
<br>
<br>
<br>

<h1>"Add perk"</h1>
<h3>"Insert perk id"</h3>
<form action="Controller?command=add_perk_for_client&client=${client_id}" method="post">
<br>
<label>Perk ID</label>
<br>
<input name="perk_id" type="string"/>
<br>
<br>
<input type="submit" value="add perk"/>
</form>
<br>
<br>
<br>

<h2>"All Perks for Client"</h2>
<ul>
        <c:forEach var="perk" items="${perks}">
                <li>
                    <c:out value="${perk}"/>
                    <br></br>
                    </a>
                </li>
            </c:forEach>
    <br></br>
</ul>
<br>

<h2>"Stay Coast for Client"</h2>
<h2>"${coast}"</h2>

<a href="/">Back</a>
</body>
</html>



