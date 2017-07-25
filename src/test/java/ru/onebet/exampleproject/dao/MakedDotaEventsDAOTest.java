package ru.onebet.exampleproject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.dao.betmakerdao.DotaBetsMakerDAO;
import ru.onebet.exampleproject.dao.betsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MakedDotaEventsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO daoC;

    @Autowired
    private DotaEventsDAO daoB;

    @Autowired
    private UserDAOImpl daoU;

    @Autowired
    private DotaBetsMakerDAO daoM;

    @Autowired
    private CheckOperations sCheck;

    @Test
    public void testMakeBet() throws Exception {

        ClientImpl clientImpl = daoU.createClient(
                "userOne",
                "123456",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        clientImpl.Mutate(clientImpl)
                .balance(new BigDecimal("250.00"))
                .build();

        em.getTransaction().commit();

        ClientImpl root = daoU.ensureRootUser();

        DotaTeam teamOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam teamTwo = daoC.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoB.createEvent("EG",
                "VP",
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoM.makeBet(clientImpl,
                event,
                teamOne,
                new BigDecimal("200.00"));

        assertEquals(1, em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(new BigDecimal("50.00"), clientImpl.getBalance());
        assertEquals(new BigDecimal("200.00"),root.getBalance());

        daoM.makeBet(clientImpl,
                event,
                teamOne,
                new BigDecimal("45.00"));

        assertEquals(2, em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(new BigDecimal("5.00"), clientImpl.getBalance());
        assertEquals(new BigDecimal("245.00"),root.getBalance());

    }

    @Test
    public void testAllMakedBets() throws Exception {
        assertEquals(daoM.allMakedBets().size(), em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(daoM.allMakedBets().size(), 0);

        ClientImpl clientImpl = daoU.createClient(
                "userOne",
                "123456",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        clientImpl.Mutate(clientImpl)
                .balance(new BigDecimal("250.00"))
                .build();
        em.getTransaction().commit();

        ClientImpl root = daoU.ensureRootUser();

        DotaTeam teamOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam teamTwo = daoC.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent bet = daoB.createEvent("EG",
                "VP",
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoM.makeBet(clientImpl,
                bet,
                teamOne,
                new BigDecimal("200.00"));

        assertEquals(daoM.allMakedBets().size(), 1);
        assertEquals(clientImpl.getBets().size(),1);

        daoM.makeBet(clientImpl,
                bet,
                teamOne,
                new BigDecimal("20.00"));
        assertEquals(daoM.allMakedBets().size(), 2);
    }
}
