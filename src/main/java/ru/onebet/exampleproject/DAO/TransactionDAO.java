package ru.onebet.exampleproject.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.Transaction;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionDAO {
    private final EntityManager em;

    @Autowired
    public TransactionDAO(EntityManager em) {
        this.em = em;
    }

    @Autowired
    public UserDAO daoU;

    public void emitMoney(String amount) {
        if (new BigDecimal(amount).max(new BigDecimal("0.0")) == new BigDecimal("0.0")) throw new IllegalArgumentException();

        em.getTransaction().begin();

        try {
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = new Transaction();
            t.setDate(new Date());
            t.setAmount(new BigDecimal(amount));
            t.setUser(root);
            t.setRoot(root);

            em.persist(t);
            em.refresh(root);

            root.setBalance(root.getBalance().add(new BigDecimal(amount)));

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(User user, String amount) {
        if (new BigDecimal(amount).max(new BigDecimal("0.0")) == new BigDecimal("0.0")) throw new IllegalArgumentException();

        em.getTransaction().begin();

        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            if (user.getBalance().max(new BigDecimal(amount)) == new BigDecimal(amount)) throw new IllegalArgumentException("No have money on balance");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = new Transaction();
            t.setDate(new Date());
            t.setAmount(new BigDecimal(amount));
            t.setUser(user);
            t.setRoot(root);

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.setBalance(user.getBalance().subtract(new BigDecimal(amount)));
            root.setBalance(root.getBalance().add(new BigDecimal(amount)));

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (User user, String amount) {
        if (new BigDecimal(amount).max(new BigDecimal("0.0")) == new BigDecimal("0.0")) throw new IllegalArgumentException();

        em.getTransaction().begin();

        UserDAO daoU = new UserDAO(em);
        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");
            if (root.getBalance().max(new BigDecimal(amount)) == new BigDecimal(amount)) throw new IllegalArgumentException("Please do emitMoney");

//            if (amount > root.getBalance()) emitMoney(amount);

            Transaction t = new Transaction();
            t.setDate(new Date());
            t.setAmount(new BigDecimal(amount));
            t.setUser(user);
            t.setRoot(root);

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.setBalance(user.getBalance().add(new BigDecimal(amount)));
            root.setBalance(root.getBalance().subtract(new BigDecimal(amount)));

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
