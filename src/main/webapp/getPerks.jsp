<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>HotelApp</title>
</head>
<body>

    <div>
        <h1>"List of available Command"</h1>
    </div>
    <div>
        <h2>"please choose the command":</h2>
            <div>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=get_perks_by_ID'">"get_perks_by_ID"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=get_perks_by_name'">"get_perks_by_name"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=get_perks_by_price'">"get_perks_by_price"</button>
                <br></br>
            </div>
    </div>
</body>
</html>