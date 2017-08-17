<jsp:useBean id="ta" type="ru.onebet.exampleproject.dto.TransactionDTO" scope="request" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <title></title>
</head>
<body>
    <table border="1" class="transactionsTable">
        <caption>Transactions</caption>
        <c:forEach var="transaction" items="${ta.transaction}">
            <tr>
                <td>${transaction.toString()}</td>
            </tr>
        </c:forEach>
    </table>

    <form method="post" action="<c:url value="/OneBet.ru/admin/list-transaction-of-user"/>">
        <input placeholder="enter login" required type="text" name="login">
        <input class="takeAdmin" type="submit" value="View personal transactions"/>
        <security:csrfInput/>
    </form>

    <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
        <input class="takeClient" type="submit" value="Return to homepage"/>
    </form>
</body>
</html>
