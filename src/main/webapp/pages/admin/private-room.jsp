<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="user" type="ru.onebet.exampleproject.dto.AdminDTO" scope="request" />
<jsp:useBean id="root" type="ru.onebet.exampleproject.dto.AdminDTO" scope="request" />

<html>
<head>
    <title>Private room</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
</head>
<body>
        <p>Login: ${user.admin.login}</p>
        <p>Firstname: ${user.admin.firstName}</p>
        <p>Lasname: ${user.admin.lastName}</p>
        <p>Email: ${user.admin.email}</p>
        <p>Balance: ${user.admin.balance}</p>
        <p>Root balance: ${root.admin.balance}</p>

        <form method="get" action="<c:url value="/admin/update-admins-details"/>">
            <input class="takeAdmin" type="submit" value="Update other information"/>
        </form>

        <form method="get" action="<c:url value="/admin/to-create-dota-team-page"/>">
            <input class="takeAdmin" type="submit" value="Create a new team"/>
        </form>

        <form method="post" action="<c:url value="/admin/to-update-dota-team-page"/>">
            <input placeholder="enter teamname" required type="text" name="teamNameForUpdate" id="teamNameForUpdate">
            <input class="takeAdmin" type="submit" value="Update a team"/>
            <security:csrfInput/>
        </form>

        <form method="get" action="<c:url value="/to-home-page"/>">
            <input class="takeClient" type="submit" value="Return to homepage"/>
        </form>

        <form method="get" action="<c:url value="/admin/list-of-all-dota-team"/>">
            <input class="takeAdmin" type="submit" value="See all created Dota team"/>
        </form>

        <form method="get" action="<c:url value="/admin-root/to-create-new-admin-page"/>">
            <input class="takeAdmin" type="submit" value="Create new admin"/>
        </form>

        <form method="get" action="<c:url value="/admin-root/to-emit-money-page"/>">
            <input class="takeAdmin" type="submit" value="Emit money"/>
        </form>

</body>
</html>
