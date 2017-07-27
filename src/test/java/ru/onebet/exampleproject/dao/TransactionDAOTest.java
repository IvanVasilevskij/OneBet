package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class TransactionDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAOImpl daoUser;

    @Autowired
    private TransactionDAO daoTransaction;

    @Test
    public void testEmitMoney() throws Exception {

        Admin root = daoUser.ensureRootUser();

        daoTransaction.emitMoney(root, new BigDecimal("500.00"));


        assertEquals(new BigDecimal("500.00"), daoUser.findAdmin(Admin.RootAdminName).getBalance());
        assertEquals(new BigDecimal("500.00"), daoUser.findAdmin(Admin.RootAdminName).getTransactions().get(0).getAmount());

        daoTransaction.emitMoney(root, new BigDecimal("250.00"));

        assertEquals(new BigDecimal("750.00"), daoUser.findAdmin(Admin.RootAdminName).getBalance());
        assertEquals(new BigDecimal("250.00"), daoUser.findAdmin(Admin.RootAdminName).getTransactions().get(1).getAmount());
    }

    @Test
    public void testSendMoney() throws Exception {

        Admin root = daoUser.ensureRootUser();

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");

        em.getTransaction().begin();

        client.mutator(client)
                .withBalance(client.getBalance().add(new BigDecimal("150.00")))
                .mutate();

        em.getTransaction().commit();

        daoTransaction.sendMoney(client, new BigDecimal("100.00"));

        assertEquals(new BigDecimal("50.00"), client.getBalance());
        assertEquals(new BigDecimal("100.00"), root.getBalance());
        assertEquals(new BigDecimal("100.00"), client.getTransactions().get(0).getAmount());

        daoTransaction.sendMoney(client,new BigDecimal("50.00"));

        assertEquals(new BigDecimal("0.00"), client.getBalance());
        assertEquals(new BigDecimal("150.00"), root.getBalance());
        assertEquals(new BigDecimal("50.00"), client.getTransactions().get(1).getAmount());
    }

    @Test
    public void testReciveMoney() throws Exception {

        Admin root = daoUser.ensureRootUser();

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");


        daoTransaction.reciveMoney(client, admin, new BigDecimal("100.00"));

        assertEquals(new BigDecimal("100.00"), client.getBalance());
        assertEquals(new BigDecimal("-100.00"), root.getBalance());
        assertEquals(new BigDecimal("100.00"), client.getTransactions().get(0).getAmount());

        daoTransaction.emitMoney(root, new BigDecimal("500.00"));

        daoTransaction.reciveMoney(client, admin, new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), client.getBalance());
        assertEquals(new BigDecimal("350.00"), root.getBalance());
        assertEquals(new BigDecimal("50.00"), client.getTransactions().get(1).getAmount());
        assertEquals(new BigDecimal("00.00"), admin.getBalance());
    }

    @Test
    public void testCheckBalanceForPayoutPrize() throws Exception {
        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");

        em.getTransaction().begin();

        admin.mutator(admin)
                .withBalance(new BigDecimal("150.00"))
                .mutate();

        em.persist(admin);
        em.getTransaction().commit();

        assertEquals(new BigDecimal("150.00"),admin.getBalance());

        daoTransaction.checkBalanceForPayoutPrize(admin, new BigDecimal("200.00"));

        assertEquals(new BigDecimal("200.00"),admin.getBalance());

    }
}
