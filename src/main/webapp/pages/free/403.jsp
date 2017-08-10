<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>Sorry, you do not have permission to view this page.</h2>

    <form method="get" action="<c:url value="/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
