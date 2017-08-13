<jsp:useBean id="team" type="ru.onebet.exampleproject.dto.DotaTeamDTO" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>All Dota team:</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">

</head>
<body>
    <c:forEach var="team" items="${team.allTeam}">
        <p>${team}</p>
    </c:forEach>

    <form method="post" action="<c:url value="/OneBet.ru/admin/to-update-dota-team-page"/>">
        <input placeholder="enter teamname" required type="text" name="teamNameForUpdate" id="teamNameForUpdate">
        <input class="takeAdmin" type="submit" value="Update team"/>
        <security:csrfInput/>
    </form>

    <form method="post" action="<c:url value="/OneBet.ru/admin/delete-dota-team"/>">

        <input placeholder="enter teamname" required type="text" name="teamForDelete" id="teamForDelete">
        <input class="takeAdmin" type="submit" value="Delete team"/>
        <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
