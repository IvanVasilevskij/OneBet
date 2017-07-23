package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.checks.CheckOperationsAboutBigDecimal;
import ru.onebet.exampleproject.model.Transaction;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionDAO {
    private final EntityManager em;
    private UserDAO daoU;
    private CheckOperationsAboutBigDecimal sCheck;

    @Autowired
    public TransactionDAO(EntityManager em,
                          UserDAO daoU,
                          CheckOperationsAboutBigDecimal sCheck) {
        this.em = em;
        this.daoU = daoU;
        this.sCheck = sCheck;
    }

    public void emitMoney(String amount) {

        BigDecimal amountBd = sCheck.beeSureThatAmountMoreThenZero(amount);

        em.getTransaction().begin();

        try {
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = Transaction.newBuilder()
                    .date(new Date())
                    .amount(amountBd)
                    .user(root)
                    .root(root)
                    .build();

            em.persist(t);
            em.refresh(root);

            root.newBuilder(root)
                    .balance(root.getBalance().add(amountBd))
                    .build();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(User user, String amount) {

        BigDecimal amountBd = sCheck.beeSureThatAmountMoreThenZero(amount);

        em.getTransaction().begin();

        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            if (user.getBalance().max(amountBd) == amountBd) throw new IllegalArgumentException("No have money on balance");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");

            Transaction t = Transaction.newBuilder()
                    .date(new Date())
                    .amount(amountBd)
                    .user(user)
                    .root(root)
                    .build();

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.newBuilder(user)
                    .balance(user.getBalance().subtract(amountBd))
                    .build();

            root.newBuilder(root)
                    .balance(root.getBalance().add(amountBd))
                    .build();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (User user, String amount) {

        BigDecimal amountBd = sCheck.beeSureThatAmountMoreThenZero(amount);

        em.getTransaction().begin();

        try {
            if (daoU.findUser(user.getLogin()) == null) throw new IllegalStateException("No  user");
            User root = daoU.findUser(User.RootUserName);
            if (root == null) throw new IllegalStateException("No root user");
            if (root.getBalance().max(new BigDecimal(amount)) == new BigDecimal(amount)) throw new IllegalArgumentException("Please do emitMoney");

//            if (amount > root.getBalance()) emitMoney(amount);

            Transaction t = Transaction.newBuilder()
                    .date(new Date())
                    .amount(amountBd)
                    .user(user)
                    .root(root)
                    .build();

            em.persist(t);
            em.refresh(user);
            em.refresh(root);

            user.newBuilder(user)
                    .balance(user.getBalance().add(amountBd))
                    .build();

            root.newBuilder(root)
                    .balance(root.getBalance().subtract(amountBd))
                    .build();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
