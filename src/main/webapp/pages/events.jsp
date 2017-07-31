<jsp:useBean id="events" type="ru.onebet.exampleproject.EventsBean" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The event's that are occuring or have occured on out website!</title>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">
</head>
<body>
    <p align="center">List of all events:</p>
    <c:forEach var="event" items="${events.events}">
        <p align="center">Event: ${event}</p>
    </c:forEach>
</body>
</html>
