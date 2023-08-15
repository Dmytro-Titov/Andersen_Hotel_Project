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
<h1>"Perk"</h1>
<ul>
    <p>"${perk_id}"</p>
</ul>


<h1>"Change Price"</h1>
<h3>"Insert price"</h3>
<form action="Controller?command=change_price_for_perk&perk=${perk_id}" method="post">
<br>
<label>Price</label>
<br>
<input name="price" type="string"/>
<br>
<br>
<input type="submit" value="change price"/>
</form>
<br>
<br>

<a href="/">Back</a>
</body>
</html>



