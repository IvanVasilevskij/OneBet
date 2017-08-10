package ru.onebet.exampleproject.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.dao.betmakerdao.DotaBetsMakerDAO;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;
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
@WebAppConfiguration
@EnableWebSecurity
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

    @Test
    public void testMakeBet() throws Exception {

        em.getTransaction().begin();
        DotaTeam teamFirst = daoTeamDota.createTeam("EG");
        DotaTeam teamSecond = daoTeamDota.createTeam("VP");

        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");

        client = ClientImpl.mutator(client)
                .withBalance(new BigDecimal("250.00"))
                .mutate();

        Admin root = daoUser.ensureRootUser();
        em.getTransaction().commit();

        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        em.getTransaction().begin();
        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                0,
                24.7);
        em.getTransaction().commit();

        em.getTransaction().begin();
        daoBetsMakerDota.makeBet(client,
                root,
                event,
                teamFirst,
                new BigDecimal("200.00"));
        em.getTransaction().commit();

        assertEquals(1, em.createQuery("from MakedBetsOfDota", MakedBetsOfDota.class).getResultList().size());

        em.getTransaction().begin();
        daoBetsMakerDota.makeBet(client,
                root,
                event,
                teamSecond,
                new BigDecimal("45.00"));
        em.getTransaction().commit();

        assertEquals(2, em.createQuery("from MakedBetsOfDota", MakedBetsOfDota.class).getResultList().size());
        assertEquals(2, client.getBets().size());
    }

    @Test
    public void testAllMakedBets() throws Exception {

        assertEquals(daoBetsMakerDota.allMakedBets(), em.createQuery("from MakedBetsOfDota", MakedBetsOfDota.class).getResultList());
        assertEquals(daoBetsMakerDota.allMakedBets().size(), 0);

        em.getTransaction().begin();
        DotaTeam teamFirst = daoTeamDota.createTeam("EG");
        DotaTeam teamSecond = daoTeamDota.createTeam("VP");

        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");

        client = ClientImpl.mutator(client)
                .withBalance(new BigDecimal("250.00"))
                .mutate();
        em.persist(client);

        Admin root = daoUser.ensureRootUser();
        em.getTransaction().commit();

        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        em.getTransaction().begin();
        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                0,
                24.7);
        em.getTransaction().commit();

        em.getTransaction().begin();
        daoBetsMakerDota.makeBet(client,
                root,
                event,
                teamSecond,
                new BigDecimal("200.00"));
        em.getTransaction().commit();

        assertEquals(daoBetsMakerDota.allMakedBets(), em.createQuery("from MakedBetsOfDota", MakedBetsOfDota.class).getResultList());
        assertEquals(daoBetsMakerDota.allMakedBets().size(), 1);
        assertEquals(client.getBets().size(),1);

        em.getTransaction().begin();
        daoBetsMakerDota.makeBet(client,
                root,
                event,
                teamFirst,
                new BigDecimal("20.00"));
        em.getTransaction().commit();

        assertEquals(daoBetsMakerDota.allMakedBets().size(), 2);
    }
}
