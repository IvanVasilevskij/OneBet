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

    @Autowired
    public TransactionDAO(EntityManager em) {
        this.em = em;
    }

    void emitMoney(Admin admin, ClientImpl clientForEmitMoneyOperation, BigDecimal amount) {
        try {
            Transaction t = Transaction.Builder()
                    .withDate(LocalDateTime.now())
                    .withAmount(amount)
                    .withClient(clientForEmitMoneyOperation)
                    .withAdmin(admin)
                    .build();

            em.persist(t);
            em.persist(clientForEmitMoneyOperation);
            em.refresh(admin);

            admin = Admin.mutator(admin)
                    .withBalance(admin.getBalance().add(amount))
                    .mutate();
            em.persist(admin);

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void sendMoney(ClientImpl client, Admin root, BigDecimal amount) {
        try {
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

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public void reciveMoney (ClientImpl client, Admin root, BigDecimal amount) {
        try {
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

        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    void checkBalanceForPayoutPrize(Admin admin, ClientImpl clientForEmitMoneyOperation, BigDecimal amount) {
        if (admin.getBalance().compareTo(amount) < 0) {
            BigDecimal shortage = amount.subtract(admin.getBalance());
            emitMoney(admin, clientForEmitMoneyOperation, shortage);
            em.persist(admin);
        }
    }
}
