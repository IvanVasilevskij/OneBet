package ru.onebet.exampleproject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.DotaBets;
import ru.onebet.exampleproject.model.DotaTeam;
import ru.onebet.exampleproject.model.User;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MakedDotaBetsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO daoC;

    @Autowired
    private DotaBetsDAO daoB;

    @Autowired
    private UserDAO daoU;

    @Autowired
    private DotaBetsMakerDAO daoM;

    @Autowired
    private CheckOperations sCheck;

    @Test
    public void testMakeBet() throws Exception {

        User user = daoU.createUser(
                "userOne",
                "123456",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.mutate(user)
                .balance(new BigDecimal("250.00"))
                .build();

        em.getTransaction().commit();

        User root = daoU.ensureRootUser();

        DotaTeam comandOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam comandTwo = daoC.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaBets bet = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoM.makeBet(user,
                bet,
                comandOne,
                new BigDecimal("200.00"));

        assertEquals(1, em.createQuery("from DotaBetsMaked ").getResultList().size());
        assertEquals(new BigDecimal("50.00"),user.getBalance());
        assertEquals(new BigDecimal("200.00"),root.getBalance());

        daoM.makeBet(user,
                bet,
                comandOne,
                new BigDecimal("45.00"));

        assertEquals(2, em.createQuery("from DotaBetsMaked ").getResultList().size());
        assertEquals(new BigDecimal("5.00"),user.getBalance());
        assertEquals(new BigDecimal("245.00"),root.getBalance());

    }

    @Test
    public void testAllMakedBets() throws Exception {
        assertEquals(daoM.allMakedBets().size(), em.createQuery("from DotaBetsMaked ").getResultList().size());
        assertEquals(daoM.allMakedBets().size(), 0);

        User user = daoU.createUser(
                "userOne",
                "123456",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        user.mutate(user)
                .balance(new BigDecimal("250.00"))
                .build();
        em.getTransaction().commit();

        User root = daoU.ensureRootUser();

        DotaTeam comandOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam comandTwo = daoC.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaBets bet = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoM.makeBet(user,
                bet,
                comandOne,
                new BigDecimal("200.00"));

        assertEquals(daoM.allMakedBets().size(), 1);
        assertEquals(user.getBets().size(),1);

        daoM.makeBet(user,
                bet,
                comandOne,
                new BigDecimal("20.00"));
        assertEquals(daoM.allMakedBets().size(), 2);
    }
}
