package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.model.User;
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
    private UserDAO daoU;

    @Autowired
    private TransactionDAO daoT;


    @Test
    public void testEmitMoney() throws Exception {

        User root = daoU.ensureRootUser();

        daoT.emitMoney("500.00");


        assertEquals(new BigDecimal("500.00"), daoU.findUser(User.RootUserName).getBalance());
        assertEquals(new BigDecimal("500.00"),daoU.findUser(User.RootUserName).getTransactions().get(0).getAmount());

        daoT.emitMoney("250.00");

        assertEquals(new BigDecimal("750.00"), daoU.findUser(User.RootUserName).getBalance());
        assertEquals(new BigDecimal("250.00"),daoU.findUser(User.RootUserName).getTransactions().get(1).getAmount());
    }

    @Test
    public void testSendMoney() throws Exception {

        User root = daoU.ensureRootUser();

        User user = daoU.createUser(
                "user",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.newBuilder(user)
                .balance(new BigDecimal("150.00"))
                .build();

        em.getTransaction().commit();

        daoT.sendMoney(user, "100.00");

        assertEquals(new BigDecimal("50.00"), daoU.findUser(user.getLogin()).getBalance());
        assertEquals(new BigDecimal("100.00"), daoU.findUser(User.RootUserName).getBalance());
        assertEquals(new BigDecimal("100.00"),daoU.findUser(user.getLogin()).getTransactions().get(0).getAmount());

        daoT.sendMoney(user,"50.00");

        assertEquals(new BigDecimal("0.00"), daoU.findUser(user.getLogin()).getBalance());
        assertEquals(new BigDecimal("150.00"), daoU.findUser(User.RootUserName).getBalance());
        assertEquals(new BigDecimal("50.00"),daoU.findUser(user.getLogin()).getTransactions().get(1).getAmount());
    }

    @Test
    public void testReciveMoney() throws Exception {
        User root = daoU.ensureRootUser();

        daoT.emitMoney("500.00");

        User user = daoU.createUser(
                "user",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");


        daoT.reciveMoney(user, "100.00");

        assertEquals(new BigDecimal("100.00"), daoU.findUser(user.getLogin()).getBalance());
        assertEquals(new BigDecimal("400.00"), daoU.findUser(User.RootUserName).getBalance());
        assertEquals(new BigDecimal("100.00"),daoU.findUser(user.getLogin()).getTransactions().get(0).getAmount());

        daoT.reciveMoney(user, "50.00");

        assertEquals(new BigDecimal("150.00"), daoU.findUser(user.getLogin()).getBalance());
        assertEquals(new BigDecimal("350.00"), daoU.findUser(User.RootUserName).getBalance());
        assertEquals(new BigDecimal("50.00"),daoU.findUser(user.getLogin()).getTransactions().get(1).getAmount());
    }
}
