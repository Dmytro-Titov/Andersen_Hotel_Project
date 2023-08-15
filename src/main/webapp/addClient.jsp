<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Client</title>
</head>
<body>
<h3>"Insert name and quantity"</h3>
<form action="Controller?command=insert_client" method="post">
<label>Name</label>
<br>
<input name="name" type="string"/>
<br>
<br>
<label>Quantity</label>
<br>
<input name="quantity" type="string"/>
<br>
<br>
<input type="submit" value="add client"/>
</form>
<br>
<br>
<a href="/">Back</a>
</body>
</html>