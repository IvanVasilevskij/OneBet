package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.Transaction;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionDAO {
    private final EntityManager em;
    private UserDAO daoU;

    @Autowired
    public TransactionDAO(EntityManager em,
                          UserDAO daoU) {
        this.em = em;
        this.daoU = daoU;
    }

    public void emitMoney(BigDecimal amount) {

        em.getTransaction().begin();

        try {
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = Transaction.newBuilder()
                    .date(new Date())
                    .amount(amount)
                    .user(root)
                    .root(root)
                    .build();

            em.persist(t);
            em.refresh(root);

            root.mutate(root)
                    .balance(root.getBalance().add(amount))
                    .build();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(User user, BigDecimal amount) {

        em.getTransaction().begin();

        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            if (user.getBalance().max(amount) == amount) throw new IllegalArgumentException("No have money on balance");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = Transaction.newBuilder()
                    .date(new Date())
                    .amount(amount)
                    .user(user)
                    .root(root)
                    .build();

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.mutate(user)
                    .balance(user.getBalance().subtract(amount))
                    .build();

            root.mutate(root)
                    .balance(root.getBalance().add(amount))
                    .build();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (User user, BigDecimal amount) {

        em.getTransaction().begin();

        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");
            if (root.getBalance().max(amount) == amount) throw new IllegalArgumentException("Please do emitMoney");

//            if (amount > root.getBalance()) emitMoney(amount);

            Transaction t = Transaction.newBuilder()
                    .date(new Date())
                    .amount(amount)
                    .user(user)
                    .root(root)
                    .build();

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.mutate(user)
                    .balance(user.getBalance().add(amount))
                    .build();

            root.mutate(root)
                    .balance(root.getBalance().subtract(amount))
                    .build();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
