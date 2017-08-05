<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creating user</title>
</head>
<body>
    <h1>Please enter other information</h1>
    <p>Please, fill in the fields below</p>

    <form method="post" action="/">

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
    </form>

    <form method="get" action="/private-room">
        <input type="submit" value="Enter in private room"/>
    </form>

    <p>
        <a href="../free/home-page.jsp">Return to the main page</a>
    </p>
</body>
</html>
