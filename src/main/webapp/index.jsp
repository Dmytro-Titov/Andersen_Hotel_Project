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
                <button onclick="location.href='/AndersenHotelProject/Controller?command=add_client'">"add client"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=get_clients'">"get_clients"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=add_apartment'">"add_apartment"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=get_apartments'">"get_apartments"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=add_perk'">"add_perk"</button>
                <br></br>
                <button onclick="location.href='/AndersenHotelProject/Controller?command=get_perks'">"get_perks"</button>
                <br></br>
            </div>
    </div>
</body>
</html>