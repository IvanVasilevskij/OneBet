package ru.onebet.exampleproject.dao;

import org.junit.Before;
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
    private DotaEventsDAO daoEventDota;

    @Autowired
    private DotaTeamDAO daoTeam;

    @Autowired
    private CheckOperations sCheck;

    @Before
    public void createTwoTeam() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam("EG");

        DotaTeam teamSecond = daoTeam.createTeam("VP");
    }

    @Test
    public void testCreateEvent() throws Exception {

        DotaTeam teamFirst = daoTeam.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeam.findTeamByTeamName("VP");

        LocalDateTime timeOfTheEvent = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheEvent,
                75.3,
                12.8,
                21.9);

        assertEquals(1, em.createQuery("from DotaEvent").getResultList().size());
    }

    @Test
    public void testAllBets() throws Exception {

        DotaTeam teamFirst = daoTeam.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeam.findTeamByTeamName("VP");

        LocalDateTime timeOfTheFirstEvent = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheFirstEvent,
                75.3,
                12.8,
                21.9);

        assertEquals(1, daoEventDota.allEvents());

        LocalDateTime timeOfTheSecondEvent = sCheck.tryToParseDateFromString("25.05.2015 19:30");

        DotaEvent eventTwo = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheSecondEvent,
                75.3,
                12.8,
                21.9);

        assertEquals(2, daoEventDota.allEvents());
    }

    @Test
    public void testChooseAllEventInEnteredDate() throws Exception {

        DotaTeam teamFirst = daoTeam.findTeamByTeamName("EG");
        DotaTeam teamSecond = daoTeam.findTeamByTeamName("VP");

        LocalDateTime timeOfTheFirstEvent = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        DotaEvent eventFirst = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheFirstEvent,
                75.3,
                12.8,
                21.9);

        LocalDateTime timeOfTheSecondEvent = sCheck.tryToParseDateFromString("25.06.2015 16:30");

        DotaEvent eventSecond = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheSecondEvent,
                75.3,
                12.8,
                21.9);

        LocalDateTime date = sCheck.tryToParseDateFromString("25.06.2015");

        List<DotaEvent> resultList = daoEventDota.chooseAllEventInEnteredDate(date);

        assertEquals(resultList.size(),1);
    }
}
