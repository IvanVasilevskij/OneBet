<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bet successfully made!</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">

</head>
<body>
    <h1>Bet made: ${requestScope["bet"]}</h1>

    <form method="get" action="<c:url value="/OneBet.ru/client/private-room"/>">
        <input class="takeClient" type="submit" value="Return in private room"/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>

</body>
</html>
