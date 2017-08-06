<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creating user</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.css" type="text/css">
</head>
<body background="../../img/background.jpg">
<div class="login">
    <img src="/img/face.jpg">
<form:form action="/login" method="post">
        <div class="login-input">
            <input type="text" name="login" placeholder="enter the login">
        </div>
        <div class="login-input">
            <input type="password" name="password" placeholder="enter the password">
        </div>
        <input class="login-submit" type="submit"/><br/>
        <security:csrfInput/>
</form:form>
</div>
</body>
</html>
