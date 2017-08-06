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

    <form:form method="post" action="/update-user-informations">

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
        <security:csrfInput/>
    </form:form>

    <form:form method="get" action="/private-room">
        <input type="submit" value="Enter in private room"/>
    </form:form>

    <form:form method="get" action="/tohomepage">
        <input type="submit" value="Return to homepage"/>
    </form:form>
</body>
</html>
