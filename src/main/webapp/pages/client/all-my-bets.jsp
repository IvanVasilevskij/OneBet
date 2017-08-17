<jsp:useBean id="bets" type="ru.onebet.exampleproject.dto.BetDTO" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <title></title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
<table border="1" class="betsTable">
    <caption>Your bets</caption>
    <c:forEach var="bet" items="${bets.bets}">
        <tr>
            <td>${bet.toString()}</td>
        </tr>
    </c:forEach>
</table>

<form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
    <input class="takeClient" type="submit" value="Return to homepage"/>
</form>
</body>
</html>
