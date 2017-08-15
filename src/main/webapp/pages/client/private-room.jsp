<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="ru.onebet.exampleproject.dto.ClientDTO" scope="request" />
<jsp:useBean id="ta" type="ru.onebet.exampleproject.dto.TransactionDTO" scope="request" />


<html>
    <head>
        <title>Private room</title>
        <link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css">
    </head>
    <body class="bodyPrivateRoom">
            <p>Login: ${user.client.login}</p>
            <p>Firstname: ${user.client.firstName}</p>
            <p>Lasname: ${user.client.lastName}</p>
            <p>Email: ${user.client.email}</p>
            <p>Balance: ${user.client.balance}</p>

            <table border="1" class="transactionsTable">
                <caption>Your transactions</caption>
                <c:forEach var="transaction" items="${ta.transaction}">
                    <tr>
                        <td>${transaction.toString()}</td>
                    </tr>
                </c:forEach>
            </table>

            <form method="get" action="<c:url value="/OneBet.ru/client/to-update-client-details-page"/>">
                <input class="takeClient" type="submit" value="Update other information"/>
            </form>

            <form method="get" action="<c:url value="/OneBet.ru/to-home-page"/>">
                <input class="takeClient" type="submit" value="Return to homepage"/>
            </form>
    </body>
</html>
