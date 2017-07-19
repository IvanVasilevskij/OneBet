package ru.onebet.exampleproject.DAO;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class TransactionDAOTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("postgres");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    public void testEmitMoney() throws Exception {

        UserDAO daoU = new UserDAO(em);
        User root = daoU.ensureRootUser();

        TransactionDAO daoT = new TransactionDAO(em);
        daoT.emitMoney(500.0);


        assertEquals(500.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(500.0,daoU.findUser(User.RootUserName).getTransactions().get(0).getAmount(),0.0);

        daoT.emitMoney(250.0);

        assertEquals(750.0, daoU.findUser(User.RootUserName).getBalance(),0.0);
        assertEquals(250.0,daoU.findUser(User.RootUserName).getTransactions().get(1).getAmount(),0.0);
    }

    @Test
    public void testSendMoney() throws Exception {

        UserDAO daoU = new UserDAO(em);
        daoU.ensureRootUser();

        User user = new UserDAO(em).createUser(
                "user",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.setBalance(150.0);

        em.getTransaction().commit();

        TransactionDAO daoT = new TransactionDAO(em);

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
        UserDAO daoU = new UserDAO(em);
        User root = daoU.ensureRootUser();

        TransactionDAO daoT = new TransactionDAO(em);
        daoT.emitMoney(500.0);

        User user = new UserDAO(em).createUser(
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
