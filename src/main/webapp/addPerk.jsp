<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Perk</title>
</head>
<body>
<h3>"Insert name and price"</h3>
<form action="Controller?command=insert_perk" method="post">
<label>Name</label>
<br>
<input name="name" type="string"/>
<br>
<br>
<label>Price</label>
<br>
<input name="price" type="string"/>
<br>
<br>
<input type="submit" value="add perk"/>
</form>
<br>
<br>
<a href="/">Back</a>
</body>
</html>