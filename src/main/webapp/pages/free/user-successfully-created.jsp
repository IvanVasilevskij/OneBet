<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User succesfully created!</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">

</head>
<body>
<h1>User created with login:${requestScope["login"]}</h1>

    <form method="get" action="/toHomePage">
        <input type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
