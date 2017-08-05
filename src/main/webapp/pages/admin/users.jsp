<jsp:useBean id="users" type="ru.onebet.exampleproject.dto.UserListDTO" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All created users</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body>
    <table border="1">
        <caption>Clients</caption>
        <c:forEach var="client" items="${users.clients}">
            <tr>
            <td>${client.login}</td>

                <c:forEach var="client" items="${users.clients}">
                    <c:if test="${client.email == null}">
                        <td> </td>>
                    </c:if>
                    <c:if test="${client.email != null}">
                        <td>${client.email}</td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>

    <table border="1">
        <caption>Admins</caption>
        <c:forEach var="admin" items="${users.admins}">
        <tr>
            <td>${admin.login}</td>

            <c:forEach var="admin" items="${users.admins}">
                <c:if test="${admin.email == null}">
                    <td> </td>>
                </c:if>
                <c:if test="${admin.email != null}">
                    <td>${admin.email}</td>
                </c:if>
            </c:forEach>
        </tr>
        </c:forEach>
    </table>

</body>
</html>
