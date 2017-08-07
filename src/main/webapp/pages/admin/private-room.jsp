<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="user" type="ru.onebet.exampleproject.dto.AdminDTO" scope="request" />

<html>
<head>
    <title>Private room</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
        <p>Login: ${user.admin.login}</p>
        <p>Firstname: ${user.admin.firstName}</p>
        <p>Lasname: ${user.admin.lastName}</p>
        <p>Email: ${user.admin.email}</p>
        <p>Balance: ${user.admin.balance}</p>

        <form method="get" action="/toUpdateAdminDetails">
            <input type="submit" value="Update other information"/>
        </form>

        <form method="get" action="/goCreateNewDotaTeam">
            <input type="submit" value="Create a new team"/>
        </form>

        <form method="post" action="/goUpdateDotaTeam">
            <label for="teamNameForUpdate">Teamname:</label>
            <input required type="text" name="teamNameForUpdate" id="teamNameForUpdate">
            <input type="submit" value="Update a team"/>
            <security:csrfInput/>
        </form>

        <form method="get" action="/toHomePage">
            <input type="submit" value="Return to homepage"/>
        </form>

        <form method="get" action="/toAllCreatedDotaTeamPage">
            <input type="submit" value="See all created Dota team"/>
        </form>

        <form method="get" action="/toCreateNewAdmin">
            <input type="submit" value="Create new admin"/>
        </form>

</body>
</html>
