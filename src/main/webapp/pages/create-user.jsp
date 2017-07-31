<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creating user</title>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">

</head>
    <body>
        <form method="post" action="<c:url value="/create-user"/>">
                <label>
                    <p align="center">Main parametrs of user:</p>
                    <p>
                        <input align="center" type="text" required tabindex="0" placeholder="login" name="login"/>Enter username<Br>
                    </p>
                    <p>
                        <input align="center" type="password" required tabindex="1" placeholder="password" name="enteredPassword"/>Enter password<Br>
                    <p/>
                    <p>
                        <input align="center" type="password" required tabindex="1" placeholder="password" name="repeatedPassword"/>Repeat password<Br>
                    <p/>
                </label>
            <input align="center" type="submit"/>
        </form>
    </body>
</html>
