<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incorrect teamname</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">

</head>
<body>
    <h1>Incorrect team name! This team already created!</h1>
    <form method="get" action="<c:url value="/admin/to-create-dota-team-page"/>">
        <input class="takeAdmin" type="submit" value="Return to creating tean page"/>
    </form>
</body>
</html>
