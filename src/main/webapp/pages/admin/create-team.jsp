<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Please, fill in the fields below</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">

</head>
<body>
    <form method="post" action="<c:url value="/OneBet.ru/admin/create-dota-team"/>">
            <label for="teamName">Teamname: </label>
            <input required type="text" name="teamName" id="teamName">
        <input class="login-submit" type="submit" value="Create team"/><br/>
        <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/admin/private-room"/>">
        <input class="takeAdmin" type="submit" value="Return to private room"/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/admin/list-of-all-dota-team"/>">
        <input class="takeAdmin" type="submit" value="See all Dota team"/>
    </form>
</body>
</html>
