package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.model.Transaction;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class TransactionDAO {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public TransactionDAO(EntityManager em) {
        this.em = em;
    }

    public void emitMoney(Admin admin, ClientImpl clientForEmitMoneyOperation, BigDecimal amount) {
        Transaction t = Transaction.Builder()
                .withDate(LocalDateTime.now())
                .withAmount(amount)
                .withClient(clientForEmitMoneyOperation)
                .withAdmin(admin)
                .build();

        em.persist(t);
        em.refresh(admin);
        em.refresh(clientForEmitMoneyOperation);

        admin = Admin.mutator(admin)
                .withBalance(admin.getBalance().add(amount))
                .mutate();
        em.persist(admin);
    }

    public void sendMoney(ClientImpl client, Admin root, BigDecimal amount) {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(root)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(root);

            client = ClientImpl.mutator(client)
                    .withBalance(client.getBalance().subtract(amount))
                    .mutate();
            em.persist(client);

            root = Admin.mutator(root)
                    .withBalance(root.getBalance().add(amount))
                    .mutate();
            em.persist(root);
    }

    public void reciveMoney (ClientImpl client, Admin root, BigDecimal amount) {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(root)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(root);

            client = ClientImpl.mutator(client)
                    .withBalance(client.getBalance().add(amount))
                    .mutate();

            root = Admin.mutator(root)
                    .withBalance(root.getBalance().subtract(amount))
                    .mutate();

            em.persist(client);
            em.persist(root);
    }

    public BigDecimal checkBalanceForPayoutPrize(Admin admin, BigDecimal amount) {
        if (admin.getBalance().compareTo(amount) < 0) {
            return amount.subtract(admin.getBalance());
        } else return BigDecimal.ZERO;
    }

    public List<Transaction> transactionList() {
        return em.createQuery("from Transaction " , Transaction.class).getResultList();
    }

    public List<Transaction> transactionListForClient(String login) {
        return em.createNamedQuery(Transaction.FindByLoginClient, Transaction.class)
                .setParameter("login", login)
                .getResultList();
    }

    public List<Transaction> transactionListForAdmin(String login) {
        return em.createNamedQuery(Transaction.FindByLoginAdmin, Transaction.class)
                .setParameter("login", login)
                .getResultList();
    }
}
