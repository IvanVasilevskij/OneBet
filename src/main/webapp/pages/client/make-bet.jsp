<jsp:useBean id="events" type="ru.onebet.exampleproject.dto.EventDTO" scope="request" />
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">

</head>
<body>

    <table border="1" class="transactionsTable">
        <caption>Events for bets</caption>
        <c:forEach var="event" items="${events.list}">
            <tr>
                <td>${event.toString()}</td>
            </tr>
        </c:forEach>
    </table>

    <form method="post" action="<c:url value="/OneBet.ru/client/make-bet"/>">
        <input required type="number" placeholder="Enter *id* of event" name="idOfEvent">
        <input required type="text" placeholder="team for bet" name="bettingTeam">
        <input placeholder="enter amount" required type="text" name="amount" pattern="(0\.((0[1-9]{1})|([1-9]{1}([0-9]{1})?)))|(([1-9]+[0-9]*)(\.([0-9]{1,2}))?)">

        <input class="takeClient" type="submit" value="Make!">
        <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/client/private-room"/>">
        <input class="takeAdmin" type="submit" value="Return to private room"/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
