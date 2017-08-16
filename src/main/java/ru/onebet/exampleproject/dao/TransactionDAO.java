package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
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
    private UserDAOImpl daoUser;

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

    public void sendMoney(ClientImpl client, Admin admin, BigDecimal amount) {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(admin)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(admin);

            client = ClientImpl.mutator(client)
                    .withBalance(client.getBalance().subtract(amount))
                    .mutate();
            em.persist(client);

            admin = Admin.mutator(admin)
                    .withBalance(admin.getBalance().add(amount))
                    .mutate();
            em.persist(admin);
    }

    public void reciveMoney (ClientImpl client, Admin admin, BigDecimal amount) {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(admin)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(admin);

            client = ClientImpl.mutator(client)
                    .withBalance(client.getBalance().add(amount))
                    .mutate();

            admin = Admin.mutator(admin)
                    .withBalance(admin.getBalance().subtract(amount))
                    .mutate();

            em.persist(client);
            em.persist(admin);
    }

    public BigDecimal checkBalanceForPayoutPrize(Admin admin, BigDecimal amount) {
        if (admin.getBalance().compareTo(amount) < 0) {
            return amount.subtract(admin.getBalance());
        } else return BigDecimal.ZERO;
    }

    public List<Transaction> transactionList() {
        return em.createQuery("from Transaction " , Transaction.class).getResultList();
    }

    public List<Transaction> transactionsListOfClientOrAdmin(String login) {
        List<Transaction> result;
        ClientImpl client = daoUser.findClient(login);
        if (client != null) {
            result = em.createNamedQuery(Transaction.FindByLoginClient, Transaction.class)
                    .setParameter("login", login)
                    .getResultList();
        } else {
            Admin admin = daoUser.findAdmin(login);
            if (admin != null) {
                result = em.createNamedQuery(Transaction.FindByLoginAdmin, Transaction.class)
                        .setParameter("login", login)
                        .getResultList();
            } else throw new IllegalArgumentException("");
        }
        return result;
    }
}
