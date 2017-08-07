<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter feilds below!</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.css" type="text/css">
</head>
<body background="../../img/background.jpg">
    <div class="login">
        <img src="/img/face.jpg">
        <form method="post" action="/createNewAdmin">
            <div class="login-input">
                <input type="text" name="login" pattern=".{6,}" placeholder="login more then 6 char">
            </div>
            <div class="login-input">
                <input type="password" name="enteredPassword"  pattern=".{6,}" placeholder="password more then 6 char">
            </div>
            <div class="login-input">
                <input type="password" name="repeatedPassword" placeholder="repeat the password">
            </div>
            <input class="login-submit" type="submit" value="Create!"/><br/>
            <security:csrfInput/>
        </form>
    </div>
</body>
</html>
