<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="team" type="ru.onebet.exampleproject.dto.DotaTeamDTO" scope="request" />

<html>
<head>
    <title>Please, fill in the fields below</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">

</head>
<body>
    <form method="post" action="<c:url value="/OneBet.ru/updateDotaTeamInformation"/>">
            <label>Teamname: ${team.team.teamName}</label>
        <input hidden type="text" value="${team.team.teamName}" name="teamName" id="teamName">

        <p>
            <label for="roleMid">Role mid:</label>
            <input required type="text" name="roleMid" id="roleMid">
        </p>
        <p>
            <label for="roleCarry">Role carry:</label>
            <input required type="text" name="roleCarry" id="roleCarry">
        </p>
        <p>
            <label for="roleHard">Role hard:</label>
            <input required type="text" name="roleHard" id="roleHard">
        </p>
        <p>
            <label for="roleSupFour">Role sup four position:</label>
            <input required type="text" name="roleSupFour" id="roleSupFour">
        </p>
        <p>
            <label for="roleSupFive">Role sup five position:</label>
            <input required type="text" name="roleSupFive" id="roleSupFive">
        </p>
        <input class="login-submit" type="submit" value="Update"/><br/>
        <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/admin/private-room"/>">
        <input class="takeAdmin" type="submit" value="Return to private room"/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeAdmin" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
