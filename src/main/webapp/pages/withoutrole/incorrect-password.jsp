<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incorrect password</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
    <h1>Incorrect password detected!</h1>
    <form method="get" action="<c:url value="/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
