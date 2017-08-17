<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incorrect amount</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
<h1>Incorrect amount detected!</h1>
<form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
    <input class="takeAdmin" type="submit" value="Return to homepage"/>
</form>
</body>
</html>
