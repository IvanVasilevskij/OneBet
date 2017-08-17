<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incorrect team for bet</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
<h1>Incorrect team for bet detected!</h1>
<form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
    <input class="takeAdmin" type="submit" value="Return to homepage"/>
</form>
</body>
</html>
