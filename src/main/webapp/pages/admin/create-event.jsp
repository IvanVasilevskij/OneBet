<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
    <form method="post" action="<c:url value="/OneBet.ru/admin/create-event"/>">
        <input required type="text" placeholder="team first" name="teamFirst">
        <input required type="text" placeholder="team second" name="teamSecond">
        <input required type="number" step="0.01" min="0" placeholder="persent for team first" name="persentForTeamFirst" pattern="\d+(\.\d{2})?">
        <input required type="number" step="0.01" min="0" placeholder="persent for drow" name="persentForTeamDrow" pattern="\d+(\.\d{2})?">
        <input required type="number" step="0.01" min="0" placeholder="persent for team second" name="persentForTeamSecond" pattern="\d+(\.\d{2})?">
        <input required type="text" placeholder="time of events start" name="time">

        <input class="takeAdmin" type="submit" value="Create!">
        <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/admin/private-room"/>">
        <input class="takeAdmin" type="submit" value="Return to private room"/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
