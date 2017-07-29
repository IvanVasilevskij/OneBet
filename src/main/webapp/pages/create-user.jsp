<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creating user</title>
</head>
    <body>
        <form method="post" action="/create-user">
                <label>
                    Main parametrs of user:
                    <p>
                        <input type="text" required tabindex="0" placeholder="login" name="login"/>Enter username<Br>
                    </p>

                    <p>
                        <input type="password" required tabindex="1" placeholder="password" name="enteredPassword"/>Enter password<Br>
                    <p/>
                    <p>
                        <input type="password" required tabindex="1" placeholder="password" name="repeatedPassword"/>Repeat password<Br>
                    <p/>
                </label>
            <input type="submit"/>
        </form>
    </body>
</html>
