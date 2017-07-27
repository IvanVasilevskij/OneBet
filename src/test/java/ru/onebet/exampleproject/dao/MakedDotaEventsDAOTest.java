package ru.onebet.exampleproject.dao;

import org.junit.Before;
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
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MakedDotaEventsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO daoTeam;

    @Autowired
    private DotaEventsDAO daoEventDota;

    @Autowired
    private UserDAOImpl daoUser;

    @Autowired
    private DotaBetsMakerDAO daoBetsMaker;

    @Autowired
    private CheckOperations sCheck;

    @Before
    public void createTwoTeamAndClientAndEvent() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam("EG");

        DotaTeam teamSecond = daoTeam.createTeam("VP");

        ClientImpl client = daoUser.createClient(
                "client",
                "123456",
                "vasilevskij.ivan@gmail.com");
    }


    @Test
    public void testMakeBet() throws Exception {

        ClientImpl client = daoUser.findClient("client");

        em.getTransaction().begin();

        client.Mutate(client)
                .balance(new BigDecimal("250.00"))
                .mutate();

        em.getTransaction().commit();

        Admin root = daoUser.ensureRootUser();

        DotaTeam teamFirst = daoTeam.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeam.findTeamByTeamName("VP");

        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoBetsMaker.makeBet(client,
                event,
                teamFirst,
                new BigDecimal("200.00"));

        assertEquals(1, em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(new BigDecimal("50.00"), client.getBalance());
        assertEquals(new BigDecimal("200.00"),root.getBalance());

        daoBetsMaker.makeBet(client,
                event,
                teamSecond,
                new BigDecimal("45.00"));

        assertEquals(2, em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(new BigDecimal("5.00"), client.getBalance());
        assertEquals(new BigDecimal("245.00"),root.getBalance());

    }

    @Test
    public void testAllMakedBets() throws Exception {
        assertEquals(daoBetsMaker.allMakedBets(), em.createQuery("from MakedBetsOfDota ").getResultList());
        assertEquals(daoBetsMaker.allMakedBets().size(), 0);

        ClientImpl client = daoUser.findClient("client");

        em.getTransaction().begin();

        client.Mutate(client)
                .balance(new BigDecimal("250.00"))
                .mutate();
        em.getTransaction().commit();

        Admin root = daoUser.ensureRootUser();

        DotaTeam teamFirst = daoTeam.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeam.findTeamByTeamName("VP");

        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoBetsMaker.makeBet(client,
                event,
                teamSecond,
                new BigDecimal("200.00"));

        assertEquals(daoBetsMaker.allMakedBets(), em.createQuery("from MakedBetsOfDota ").getResultList());
        assertEquals(daoBetsMaker.allMakedBets().size(), 1);
        assertEquals(client.getBets().size(),1);

        daoBetsMaker.makeBet(client,
                event,
                teamFirst,
                new BigDecimal("20.00"));

        assertEquals(daoBetsMaker.allMakedBets().size(), 2);
    }
}
