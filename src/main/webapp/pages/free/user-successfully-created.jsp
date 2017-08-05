<jsp:useBean id="client" type="ru.onebet.exampleproject.dto.ClientDTO" scope="request" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User succesfully created!</title>
</head>
<body>
<h1>User created with login:${requestScope["login"]}</h1>
<a href="home-page.jsp">Return to homepage</a>
</body>
</html>
