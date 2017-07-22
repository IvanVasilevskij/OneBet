package ru.onebet.exampleproject.DAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.Model.Bets;
import ru.onebet.exampleproject.Model.ComandOfDota;
import ru.onebet.exampleproject.Model.MakingBets;
import ru.onebet.exampleproject.Model.User;
import ru.onebet.exampleproject.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MakingBetsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ComandDAO daoC;

    @Autowired
    private BetsDAO daoB;

    @Autowired
    private UserDAO daoU;

    @Autowired
    private MakingBetsDAO daoM;

    @Test
    public void testMakeBet() throws Exception {

        User user = daoU.createUser(
                "userOne",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.setBalance(new BigDecimal("250.00"));

        em.getTransaction().commit();

        User root = daoU.ensureRootUser();


        em.getTransaction().begin();
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");
        em.persist(comandOne);
        em.getTransaction().commit();

        em.getTransaction().begin();
        ComandOfDota comandTwo = daoC.createComand(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");
        em.persist(comandTwo);
        em.getTransaction().commit();

        Bets bet = daoB.createBet("EG",
                "VP",
                "2017.09.15 16:30",
                75.3,
                12.8,
                21.9);

        daoM.makeBet("userOne",
                "password",
                "2017.09.15 16:30",
                "EG",
                "VP",
                "EG",
                "200.00");

        assertEquals(1, em.createQuery("from MakingBets ").getResultList().size());
        assertEquals(new BigDecimal("50.00"),user.getBalance());
        assertEquals(new BigDecimal("200.00"),root.getBalance());

        daoM.makeBet("userOne",
                "password",
                "2017.09.15 16:30",
                "EG",
                "VP",
                "EG",
                "45.00");

        assertEquals(2, em.createQuery("from MakingBets ").getResultList().size());
        assertEquals(new BigDecimal("5.00"),user.getBalance());
        assertEquals(new BigDecimal("245.00"),root.getBalance());

    }

    @Test
    public void testAllMakedBets() throws Exception {
        assertEquals(daoM.allMakedBets().size(), em.createQuery("from MakingBets ").getResultList().size());
        assertEquals(daoM.allMakedBets().size(), 0);

        User user = daoU.createUser(
                "userOne",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.setBalance(new BigDecimal("250.0"));

        em.getTransaction().commit();

        User root = daoU.ensureRootUser();


        em.getTransaction().begin();
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");
        em.persist(comandOne);
        em.getTransaction().commit();

        em.getTransaction().begin();
        ComandOfDota comandTwo = daoC.createComand(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");
        em.persist(comandTwo);
        em.getTransaction().commit();

        Bets bet = daoB.createBet("EG",
                "VP",
                "2017.09.15 16:30",
                75.3,
                12.8,
                21.9);

        daoM.makeBet("userOne",
                "password",
                "2017.09.15 16:30",
                "EG",
                "VP",
                "EG",
                "200.0");

        assertEquals(daoM.allMakedBets().size(), 1);
    }
}
