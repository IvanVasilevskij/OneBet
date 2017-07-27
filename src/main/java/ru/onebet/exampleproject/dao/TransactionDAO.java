package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.Transaction;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionDAO {

    private final EntityManager em;
    private UserDAOImpl daoUser;

    @Autowired
    public TransactionDAO(EntityManager em,
                          UserDAOImpl daoUser) {
        this.em = em;
        this.daoUser = daoUser;
    }

    void emitMoney(Admin admin, BigDecimal amount) {

        ClientImpl client = daoUser.ensureClientForEmitMoneyOperation();

        em.getTransaction().begin();

        try {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(admin)
                    .build();

            em.persist(t);
            em.refresh(admin);

            admin.mutator(admin)
                    .withBalance(admin.getBalance().add(amount))
                    .mutate();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(ClientImpl client, BigDecimal amount) {
        Admin root = daoUser.ensureRootUser();

        em.getTransaction().begin();

        try {
            daoUser.checkBalanceForBet(client, amount);

            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(root)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(root);

            client.mutator(client)
                    .withBalance(client.getBalance().subtract(amount))
                    .mutate();

            root.mutator(root)
                    .withBalance(root.getBalance().add(amount))
                    .mutate();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (ClientImpl client, Admin admin, BigDecimal amount) {

        Admin root = daoUser.ensureRootUser();

        em.getTransaction().begin();

        try {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(client)
                    .withAdmin(admin)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(admin);

            client.mutator(client)
                    .withBalance(client.getBalance().add(amount))
                    .mutate();

            root.mutator(root)
                    .withBalance(root.getBalance().subtract(amount))
                    .mutate();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    void checkBalanceForPayoutPrize(Admin admin, BigDecimal amount) {
        if (admin.getBalance().compareTo(amount) < 0) {
            BigDecimal shortage = amount.subtract(admin.getBalance());
            emitMoney(admin, shortage);
        }
    }
}
