<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="ru.onebet.exampleproject.dto.AdminDTO" scope="request" />

<html>
<head>
    <title>Private room</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
        <p>Login: ${user.admin.login}</p>

            <c:if test="${user.admin.firstName == null}">
                <p>Firstname: Not indicated</p>
            </c:if>
            <c:if test="${user.admin.firstName != null}}">
                <p>Firstname: ${user.admin.firstName}</p>
            </c:if>


            <c:if test="${user.admin.lastName == null}">
                <p>Lastname: Not indicated</p>
            </c:if>
            <c:if test="${user.admin.lastName != null}}">
                <p>Lasname: ${user.admin.lastName}</p>
            </c:if>


            <c:if test="${user.admin.email == null}">
                <p>Email: Not indicated</p>
            </c:if>
            <c:if test="${user.admin.email != null}}">
                <p>Email: ${user.admin.email}</p>
            </c:if>

        <p>Balance: ${user.admin.balance}</p>

    <form method="get" action="/goupdateinformation">
        <input type="submit" value="Update other information"/>
    </form>

</body>
</html>
