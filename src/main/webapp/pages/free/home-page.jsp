<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>OneBet.ru</title>
    <link rel="stylesheet" href="/css/style.css" type="text/css">
  </head>
  <body>
    Hello, You have gone to the OneBet.ru!

    <form method="get" action="/toCreateClientPage">
      <input type="submit" value="Create user"/>
    </form>

    <form method="get" action="/toLoginPage">
      <input type="submit" value="login"/>
    </form>

    <form method="get" action="/events">
      <input type="submit" value="See all events"/>
    </form>

    <form method="get" action="/users">
      <input type="submit" value="See all users"/>
    </form>

    <form method="post" action="/logout">
      <input type="submit" value="logout"/>
      <security:csrfInput/>
    </form>

    <form method="get" action="/enterClientPrivateRoom">
      <input type="submit" value="Enter in private room for Client only"/>
    </form>

    <form method="get" action="/enterAdminPrivateRoom">
      <input type="submit" value="Enter in private room for admin only"/>
    </form>

  </body>
</html>
