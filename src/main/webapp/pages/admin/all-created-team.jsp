<jsp:useBean id="team" type="ru.onebet.exampleproject.dto.DotaTeamDTO" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>All Dota team:</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">

</head>
<body>
    <c:forEach var="team" items="${team.allTeam}">
        <p>${team}</p>
    </c:forEach>

    <form method="post" action="/goUpdateDotaTeam">
        <label for="teamNameForUpdate">Teamname for updating: </label>
        <input required type="text" name="teamNameForUpdate" id="teamNameForUpdate">
        <input type="submit" value="Update team"/>
        <security:csrfInput/>
    </form>

    <form method="post" action="/deleteDotaTeam">
        <label for="teamForDelete">Teamname for delte: </label>
        <input required type="text" name="teamForDelete" id="teamForDelete">
        <input type="submit" value="Delete team"/>
        <security:csrfInput/>
    </form>

    <form method="get" action="/toHomePage">
        <input type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
