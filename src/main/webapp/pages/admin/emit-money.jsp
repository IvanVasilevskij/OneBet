<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
    <form method="post" action="<c:url value="/admin-root/emit-money"/>">
                    <input placeholder="enter amount" required type="text" name="emit" pattern="(0\.((0[1-9]{1})|([1-9]{1}([0-9]{1})?)))|(([1-9]+[0-9]*)(\.([0-9]{1,2}))?)" id="emit">
                    <input class="takeAdmin" type="submit" value="Emit"/>
        <security:csrfInput/>
    </form>
</body>
</html>
