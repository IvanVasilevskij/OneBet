<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>OneBet.ru</title>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
  </head>
  <body>
    <h1>Hello, You have gone to the OneBet.ru!</h1>

    <form method="get" action="<c:url value="/anonymous/to-create-client-page"/>">
      <input class="takeClient" type="submit" value="Create user"/>
    </form>

    <form method="get" action="<c:url value="/anonymous/to-login-page"/>">
      <input class="takeClient" type="submit" value="login"/>
    </form>

    <form method="get" action="<c:url value="/free-information/list-of-all-events"/>">
      <input class="takeClient" type="submit" value="See all events"/>
    </form>

    <form method="get" action="<c:url value="/admin/list-of-all-users"/>">
      <input class="takeAdmin" type="submit" value="See all users"/>
    </form>

    <form method="post" action="<c:url value="/logout"/>">
      <input class="takeClient" type="submit" value="logout"/>
      <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/client/private-room"/>">
      <input class="takeClient" type="submit" value="Enter in private room for Client only"/>
    </form>

    <form method="get" action="<c:url value="/admin/private-room"/>">
      <input class="takeAdmin" type="submit" value="Enter in private room for Admin only"/>
    </form>

  </body>
</html>
