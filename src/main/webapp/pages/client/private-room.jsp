<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="ru.onebet.exampleproject.dto.ClientDTO" scope="request" />

<html>
<head>
    <title>Private room</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
        <p>Login: ${user.client.login}</p>
        <p>Firstname: ${user.client.firstName}</p>
        <p>Lasname: ${user.client.lastName}</p>
        <p>Email: ${user.client.email}</p>
        <p>Balance: ${user.client.balance}</p>

    <c:forEach var="bet" items="${user.client.bets}">
        <p>Bets: ${bet}</p>
    </c:forEach>

        <form method="get" action="/toUpdateClientDetails">
            <input type="submit" value="Update other information"/>
        </form>
        
        <form method="get" action="/toHomePage">
            <input type="submit" value="Return to homepage"/>
        </form>

</body>
</html>
