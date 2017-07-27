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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MakedDotaEventsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO daoTeamDota;

    @Autowired
    private DotaEventsDAO daoEventDota;

    @Autowired
    private UserDAOImpl daoUser;

    @Autowired
    private DotaBetsMakerDAO daoBetsMakerDota;

    @Autowired
    private CheckOperations sCheck;

    @Before
    public void createTwoTeamAndClientAndEvent() throws Exception {
        DotaTeam teamFirst = daoTeamDota.createTeam("EG");

        DotaTeam teamSecond = daoTeamDota.createTeam("VP");

        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
    }


    @Test
    public void testMakeBet() throws Exception {

        ClientImpl client = daoUser.findClient("withClient");

        em.getTransaction().begin();

        client.mutator(client)
                .withBalance(new BigDecimal("250.00"))
                .mutate();

        em.getTransaction().commit();

        Admin root = daoUser.ensureRootUser();

        DotaTeam teamFirst = daoTeamDota.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeamDota.findTeamByTeamName("VP");

        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoBetsMakerDota.makeBet(client,
                event,
                teamFirst,
                new BigDecimal("200.00"));

        assertEquals(1, em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(new BigDecimal("50.00"), client.getBalance());
        assertEquals(new BigDecimal("200.00"),root.getBalance());

        daoBetsMakerDota.makeBet(client,
                event,
                teamSecond,
                new BigDecimal("45.00"));

        assertEquals(2, em.createQuery("from MakedBetsOfDota ").getResultList().size());
        assertEquals(new BigDecimal("5.00"), client.getBalance());
        assertEquals(new BigDecimal("245.00"),root.getBalance());

    }

    @Test
    public void testAllMakedBets() throws Exception {
        assertEquals(daoBetsMakerDota.allMakedBets(), em.createQuery("from MakedBetsOfDota ").getResultList());
        assertEquals(daoBetsMakerDota.allMakedBets().size(), 0);

        ClientImpl client = daoUser.findClient("withClient");

        em.getTransaction().begin();

        client.mutator(client)
                .withBalance(new BigDecimal("250.00"))
                .mutate();
        em.getTransaction().commit();

        Admin root = daoUser.ensureRootUser();

        DotaTeam teamFirst = daoTeamDota.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeamDota.findTeamByTeamName("VP");

        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        daoBetsMakerDota.makeBet(client,
                event,
                teamSecond,
                new BigDecimal("200.00"));

        assertEquals(daoBetsMakerDota.allMakedBets(), em.createQuery("from MakedBetsOfDota ").getResultList());
        assertEquals(daoBetsMakerDota.allMakedBets().size(), 1);
        assertEquals(client.getBets().size(),1);

        daoBetsMakerDota.makeBet(client,
                event,
                teamFirst,
                new BigDecimal("20.00"));

        assertEquals(daoBetsMakerDota.allMakedBets().size(), 2);
    }
}
