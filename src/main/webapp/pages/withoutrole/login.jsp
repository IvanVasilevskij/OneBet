<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creating user</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.css" type="text/css">
</head>
<body>
<div class="login">
    <img src="/img/face.jpg">
    <form method="post" action="/login">
        <div class="login-input">
            <input type="text" name="login" placeholder="enter the login">
        </div>
        <div class="login-input">
            <input type="password" name="password" placeholder="enter the password">
        </div>
        <input class="login-submit" type="submit"/><br/>
    </form>
</div>
</body>
</html>
