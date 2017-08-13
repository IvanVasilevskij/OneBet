<jsp:useBean id="transaction" type="ru.onebet.exampleproject.dto.TransactionDTO" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table border="1" class="transactionsTable">
    <caption>Clients</caption>
    <c:forEach var="transaction" items="${transaction.transaction}">
        <tr>
            <td>${transaction.toString()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
