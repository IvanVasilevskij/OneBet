<jsp:useBean id="AllEventsBean" type="ru.onebet.exampleproject.AllEventsBean" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The event's that are occuring or have occured on out website!</title>
</head>
<body>
    List of all events:

    <c:forEach var="i" begin="0" end="${AllEventsBean.events.size()}">
        <p>Event: ${AllEventsBean.events.get(i)}</p>
    </c:forEach>
</body>
</html>
