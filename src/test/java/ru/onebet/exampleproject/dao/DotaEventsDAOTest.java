package ru.onebet.exampleproject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.dao.betsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.configurations.TestConfiguration;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DotaEventsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaEventsDAO daoEvent;

    @Autowired
    private DotaTeamDAO daoTeam;

    @Autowired
    private CheckOperations sCheck;


    @Test
    public void testCreateEvent() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam teamSecond = daoTeam.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");


        LocalDateTime timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEvent.createEvent(teamFirst,
                teamSecond,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        assertEquals(1, em.createQuery("from DotaEvent").getResultList().size());
    }

    @Test
    public void testAllBets() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam teamSecond = daoTeam.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        LocalDateTime timeOfTheFirstGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEvent.createEvent(teamFirst,
                teamSecond,
                timeOfTheFirstGame,
                75.3,
                12.8,
                21.9);

        assertEquals(1, daoEvent.allEvents());

        LocalDateTime timeOfTheSecondGame = sCheck.tryToParseDateFromString("25.05.2015 19:30");

        DotaEvent eventTwo = daoEvent.createEvent(teamFirst,
                teamSecond,
                timeOfTheSecondGame,
                75.3,
                12.8,
                21.9);

        assertEquals(2, daoEvent.allEvents());
    }
}
