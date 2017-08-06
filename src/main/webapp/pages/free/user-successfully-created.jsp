<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User succesfully created!</title>
</head>
<body>
<h1>User created with login:${requestScope["login"]}</h1>

    <form method="get" action="/tohomepage">
        <input type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
