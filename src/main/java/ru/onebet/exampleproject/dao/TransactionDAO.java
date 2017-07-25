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

    public void emitMoney(Admin admin, BigDecimal amount) {

        em.getTransaction().begin();

        try {
            Transaction t = Transaction.Builder()
                    .date(LocalDateTime.now())
                    .amount(amount)
                    .admin(admin)
                    .build();

            em.persist(t);
            em.refresh(admin);

            admin.Mutate(admin)
                    .balance(admin.getBalance().add(amount))
                    .mutate();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(ClientImpl client, BigDecimal amount) {

        em.getTransaction().begin();

        try {
            daoUser.checkBalanceForBet(client, amount);

            Admin root = daoUser.ensureRootUser();

            Transaction t = Transaction.Builder()
                    .date(LocalDateTime.now())
                    .amount(amount)
                    .client(client)
                    .admin(root)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(root);

            client.Mutate(client)
                    .balance(client.getBalance().subtract(amount))
                    .mutate();

            root.Mutate(root)
                    .balance(root.getBalance().add(amount))
                    .mutate();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (ClientImpl client, Admin admin, BigDecimal amount) {

        em.getTransaction().begin();

        try {
            daoUser.checkBalanceForPayoutPrize(admin, amount);

            Admin root = daoUser.ensureRootUser();

            Transaction t = Transaction.Builder()
                    .date(LocalDateTime.now())
                    .amount(amount)
                    .client(client)
                    .admin(admin)
                    .build();

            em.persist(t);
            em.refresh(client);
            em.refresh(admin);

            client.Mutate(client)
                    .balance(client.getBalance().add(amount))
                    .mutate();

            admin.Mutate(admin)
                    .balance(admin.getBalance().subtract(amount))
                    .mutate();

            root.Mutate(root)
                    .balance(root.getBalance().subtract(amount))
                    .mutate();

            em.getTransaction().commit();

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
