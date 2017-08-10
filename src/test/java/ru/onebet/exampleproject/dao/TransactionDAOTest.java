package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
@EnableWebSecurity
public class TransactionDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAOImpl daoUser;

    @Autowired
    private TransactionDAO daoTransaction;

    @Test
    public void testEmitMoney() throws Exception {

        em.getTransaction().begin();
        Admin root = daoUser.ensureRootUser();
        em.getTransaction().commit();

        em.getTransaction().begin();
        ClientImpl clientForEmitMoneyOperation = daoUser.ensureClientForEmitMoneyOperation();
        em.getTransaction().commit();

        em.getTransaction().begin();
        daoTransaction.emitMoney(root, clientForEmitMoneyOperation, new BigDecimal("500.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("500.00"), daoUser.findAdmin(Admin.RootAdminName).getBalance());
        assertEquals(new BigDecimal("500.00"), daoUser.findAdmin(Admin.RootAdminName).getTransactions().get(0).getAmount());

        em.getTransaction().begin();
        daoTransaction.emitMoney(root, clientForEmitMoneyOperation, new BigDecimal("250.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("750.00"), daoUser.findAdmin(Admin.RootAdminName).getBalance());
        assertEquals(new BigDecimal("250.00"), daoUser.findAdmin(Admin.RootAdminName).getTransactions().get(1).getAmount());
    }

    @Test
    public void testSendMoney() throws Exception {

        em.getTransaction().begin();
        Admin root = daoUser.ensureRootUser();

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");
        em.getTransaction().commit();

        em.getTransaction().begin();
        client = ClientImpl.mutator(client)
                .withBalance(client.getBalance().add(new BigDecimal("150.00")))
                .mutate();
        em.persist(client);
        em.getTransaction().commit();

        em.getTransaction().begin();
        daoTransaction.sendMoney(client, root, new BigDecimal("100.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("50.00"), client.getBalance());
        assertEquals(new BigDecimal("100.00"), root.getBalance());
        assertEquals(new BigDecimal("100.00"), client.getTransactions().get(0).getAmount());

        em.getTransaction().begin();
        daoTransaction.sendMoney(client, root, new BigDecimal("50.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("0.00"), client.getBalance());
        assertEquals(new BigDecimal("150.00"), root.getBalance());
        assertEquals(new BigDecimal("50.00"), client.getTransactions().get(1).getAmount());
    }

    @Test
    public void testReciveMoney() throws Exception {

        em.getTransaction().begin();
        Admin root = daoUser.ensureRootUser();

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");

        ClientImpl clientForEmitMoneyOperation = daoUser.ensureClientForEmitMoneyOperation();
        em.getTransaction().commit();

        em.getTransaction().begin();
        daoTransaction.reciveMoney(client, root, new BigDecimal("100.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("100.00"), client.getBalance());
        assertEquals(new BigDecimal("-100.00"), root.getBalance());
        assertEquals(new BigDecimal("100.00"), client.getTransactions().get(0).getAmount());

        em.getTransaction().begin();
        daoTransaction.emitMoney(root, clientForEmitMoneyOperation, new BigDecimal("500.00"));
        em.getTransaction().commit();

        em.getTransaction().begin();
        daoTransaction.reciveMoney(client, root, new BigDecimal("50.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("150.00"), client.getBalance());
        assertEquals(new BigDecimal("350.00"), root.getBalance());
        assertEquals(new BigDecimal("50.00"), client.getTransactions().get(1).getAmount());
    }

    @Test
    public void testCheckBalanceForPayoutPrize() throws Exception {

        em.getTransaction().begin();
        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");
        ClientImpl clientForEmitMoneyOperation = daoUser.ensureClientForEmitMoneyOperation();
        em.getTransaction().commit();

        em.getTransaction().begin();
        admin = Admin.mutator(admin)
                .withBalance(new BigDecimal("150.00"))
                .mutate();
        em.persist(admin);
        em.getTransaction().commit();

        assertEquals(new BigDecimal("150.00"),admin.getBalance());

        em.getTransaction().begin();
        daoTransaction.checkBalanceForPayoutPrize(admin, clientForEmitMoneyOperation, new BigDecimal("200.00"));
        em.getTransaction().commit();

        assertEquals(new BigDecimal("200.00"),admin.getBalance());

    }
}
