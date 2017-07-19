package ru.onebet.exampleproject.DAO;


import ru.onebet.exampleproject.Model.Transaction;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityManager;
import java.util.Date;

public class TransactionDAO {
    private final EntityManager em;

    public TransactionDAO(EntityManager em) {
        this.em = em;
    }

    public void emitMoney(double amount) {
        if (amount < 0) throw new IllegalArgumentException();

        em.getTransaction().begin();

        UserDAO dao = new UserDAO(em);
        try {
            User root = dao.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = new Transaction();
            t.setDate(new Date());
            t.setAmount(amount);
            t.setUser(root);
            t.setRoot(root);

            em.persist(t);
            em.refresh(root);

            root.setBalance(root.getBalance() + amount);

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(User user, double amount) {
        if (amount < 0) throw new IllegalArgumentException();

        em.getTransaction().begin();

        UserDAO daoU = new UserDAO(em);
        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            if (amount > user.getBalance()) throw new IllegalArgumentException("No have money on balance");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = new Transaction();
            t.setDate(new Date());
            t.setAmount(amount);
            t.setUser(user);
            t.setRoot(root);

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.setBalance(user.getBalance()-amount);
            root.setBalance(root.getBalance()+amount);

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (User user, double amount) {
        if (amount < 0) throw new IllegalArgumentException();

        em.getTransaction().begin();

        UserDAO daoU = new UserDAO(em);
        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");
            if (amount > root.getBalance()) throw new IllegalArgumentException("Please do emitMoney");

//            if (amount > root.getBalance()) emitMoney(amount);

            Transaction t = new Transaction();
            t.setDate(new Date());
            t.setAmount(amount);
            t.setUser(user);
            t.setRoot(root);

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.setBalance(user.getBalance()+amount);
            root.setBalance(root.getBalance()-amount);

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
