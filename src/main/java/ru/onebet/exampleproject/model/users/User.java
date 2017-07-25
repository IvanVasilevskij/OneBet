package ru.onebet.exampleproject.model.users;

import ru.onebet.exampleproject.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface User {

    int getId();

    String getLogin();

    String getPassword();

    String getFirstName();

    String getLastName();

    BigDecimal getBalance();

    String getEmail();

    List<Transaction> getTransactions();

}
