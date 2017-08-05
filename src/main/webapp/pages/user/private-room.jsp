<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="client" type="ru.onebet.exampleproject.dto.ClientDTO" scope="request" />

<html>
<head>
    <title>Private room</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
    <a href="add-other-information.jsp">Change some ersonal information</a>
        <p>Firstname: ${client.client.firstName}</p>
        <p>Lastname: ${client.client.lastName}</p>
        <p>Email: ${client.client.email}</p>

    <p>Balance: ${client.client.balance}</p>

    <c:forEach var="bet" items="${client.client.bets}">
        <p>Bets: ${bet}</p>
    </c:forEach>

    <form method="get" action="/logout">
        <input type="submit" value="Logout"/>
    </form>
</body>
</html>
