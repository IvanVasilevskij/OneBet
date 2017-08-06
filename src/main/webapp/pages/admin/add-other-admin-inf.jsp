<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creating user</title>
</head>
<body>
    <h1>Please enter other information</h1>
    <p>Please, fill in the fields below</p>

    <form method="post" action="/update-admin-informations">

    <p>
        <label for="firstName">Firstname:</label>
        <input type="text" name="firstName" id="firstName">
    </p>

    <p>
        <label for="lastName">Lastname:</label>
        <input type="text" name="lastName" id="lastName">
    </p>

    <p>
        <label for="email">Email:</label>
        <input type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" name="email" id="email">
    </p>
        <input class="login-submit" type="submit" value="Update"/><br/>
        <security:csrfInput/>
    </form>

    <form method="get" action="/goprivatroom">
        <input type="submit" value="Return to private room"/>
    </form>

    <form method="get" action="/tohomepage">
        <input type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
