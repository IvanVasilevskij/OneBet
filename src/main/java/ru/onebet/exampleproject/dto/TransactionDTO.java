package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.Transaction;

import java.util.List;

public class TransactionDTO {
    private List<Transaction> transaction;

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}
