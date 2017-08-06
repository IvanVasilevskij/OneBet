<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="client" type="ru.onebet.exampleproject.dto.ClientDTO" scope="request" />

<html>
<head>
    <title>Private room</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
        <p>Login: ${client.client.login}</p>
        <p>Firstname: ${client.client.firstName}</p>
        <p>Lastname: ${client.client.lastName}</p>
        <p>Email: ${client.client.email}</p>

        <p>Balance: ${client.client.balance}</p>

    <c:forEach var="bet" items="${client.client.bets}">
        <p>Bets: ${bet}</p>
    </c:forEach>

    <form:form method="get" action="/update-user-informations">
        <input type="submit" value="Return to homepage"/>
    </form:form>

    <form:form method="get" action="/logout">
        <input type="submit" value="Logout"/>
    </form:form>
</body>
</html>
