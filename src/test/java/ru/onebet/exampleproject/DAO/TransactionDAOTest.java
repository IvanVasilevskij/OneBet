package ru.onebet.exampleproject.DAO;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.Model.User;
import ru.onebet.exampleproject.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

        daoT.emitMoney(500.0);


        assertEquals(500.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(500.0,daoU.findUser(User.RootUserName).getTransactions().get(0).getAmount(),0.0);

        daoT.emitMoney(250.0);

        assertEquals(750.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(250.0,daoU.findUser(User.RootUserName).getTransactions().get(1).getAmount(),0.0);
    }

    @Test
    public void testSendMoney() throws Exception {

        User root = daoU.ensureRootUser();

        User user = daoU.createUser(
                "user",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.setBalance(150.0);

        em.getTransaction().commit();

        daoT.sendMoney(user, 100.0);

        assertEquals(50.0, daoU.findUser(user.getLogin()).getBalance(),0.0);
        assertEquals(100.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(100.0,daoU.findUser(user.getLogin()).getTransactions().get(0).getAmount(),0.0);

        daoT.sendMoney(user,50.0);

        assertEquals(0.0, daoU.findUser(user.getLogin()).getBalance(),0.0);
        assertEquals(150.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(50.0,daoU.findUser(user.getLogin()).getTransactions().get(1).getAmount(),0.0);
    }

    @Test
    public void testReciveMoney() throws Exception {
        User root = daoU.ensureRootUser();

        daoT.emitMoney(500.0);

        User user = daoU.createUser(
                "user",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");


        daoT.reciveMoney(user, 100.0);

        assertEquals(100.0, daoU.findUser(user.getLogin()).getBalance(),0.0);
        assertEquals(400.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(100.0,daoU.findUser(user.getLogin()).getTransactions().get(0).getAmount(),0.0);

        daoT.reciveMoney(user, 50.0);

        assertEquals(150.0, daoU.findUser(user.getLogin()).getBalance(),0.0);
        assertEquals(350.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(50.0,daoU.findUser(user.getLogin()).getTransactions().get(1).getAmount(),0.0);
    }
}
