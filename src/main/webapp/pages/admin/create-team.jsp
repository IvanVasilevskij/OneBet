<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Please, fill in the fields below</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">

</head>
<body>
    <form method="post" action="/createTeam">
            <label for="teamName">Teamname: </label>
            <input required type="text" name="teamName" id="teamName">
        <input class="login-submit" type="submit" value="Create team"/><br/>
        <security:csrfInput/>
    </form>

    <form method="get" action="/enterAdminPrivateRoom">
        <input type="submit" value="Return to private room"/>
    </form>

    <form method="get" action="/toHomePage">
        <input type="submit" value="Return to homepage"/>
    </form>

    <form method="get" action="/toAllCreatedDotaTeamPage">
        <input type="submit" value="See all Dota team"/>
    </form>
</body>
</html>
