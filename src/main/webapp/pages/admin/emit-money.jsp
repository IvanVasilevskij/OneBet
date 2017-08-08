<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <form method="post" action="/emitMoney">
        <label for="emit">Enter the number:</label>
        <input required type="text" name="emit" pattern="(0\.((0[1-9]{1})|([1-9]{1}([0-9]{1})?)))|(([1-9]+[0-9]*)(\.([0-9]{1,2}))?)" id="emit">
        <input type="submit" value="Emit"/>
        <security:csrfInput/>
    </form>
</body>
</html>
