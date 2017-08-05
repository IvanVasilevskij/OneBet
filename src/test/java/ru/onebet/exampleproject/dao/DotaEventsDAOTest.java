package ru.onebet.exampleproject.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
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
    private DotaTeamDAO daoDotaTeam;

    @Autowired
    private CheckOperations sCheck;

    @Before
    public void createTwoTeam() throws Exception {
        em.getTransaction().begin();
        DotaTeam teamFirst = daoDotaTeam.createTeam("EG");
        em.getTransaction().commit();

        em.getTransaction().begin();
        DotaTeam teamSecond = daoDotaTeam.createTeam("VP");
        em.getTransaction().commit();
    }

    @Test
    public void testCreateEvent() throws Exception {

        em.getTransaction().begin();
        DotaTeam teamFirst = daoDotaTeam.findTeamByTeamName("EG");
        em.getTransaction().commit();

        em.getTransaction().begin();
        DotaTeam teamSecond = daoDotaTeam.findTeamByTeamName("VP");
        em.getTransaction().commit();

        LocalDateTime timeOfTheEvent = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        em.getTransaction().begin();
        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheEvent,
                75.3,
                12.8,
                21.9);
        em.getTransaction().commit();

        assertEquals(1, em.createQuery("from DotaEvent", DotaEvent.class).getResultList().size());
    }

    @Test
    public void testAllBets() throws Exception {

        em.getTransaction().begin();
        DotaTeam teamFirst = daoDotaTeam.findTeamByTeamName("EG");
        em.getTransaction().commit();

        em.getTransaction().begin();
        DotaTeam teamSecond = daoDotaTeam.findTeamByTeamName("VP");
        em.getTransaction().commit();

        LocalDateTime timeOfTheFirstEvent = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        em.getTransaction().begin();
        DotaEvent event = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheFirstEvent,
                75.3,
                12.8,
                21.9);
        em.getTransaction().commit();

        assertEquals(1, daoEventDota.allEvents().size());

        LocalDateTime timeOfTheSecondEvent = sCheck.tryToParseDateFromString("25.05.2015 19:30");

        em.getTransaction().begin();
        DotaEvent eventTwo = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheSecondEvent,
                75.3,
                12.8,
                21.9);
        em.getTransaction().commit();

        assertEquals(2, daoEventDota.allEvents().size());
    }

    @Test
    public void testChooseAllEventInEnteredDate() throws Exception {

        em.getTransaction().begin();
        DotaTeam teamFirst = daoDotaTeam.findTeamByTeamName("EG");
        em.getTransaction().commit();

        em.getTransaction().begin();
        DotaTeam teamSecond = daoDotaTeam.findTeamByTeamName("VP");
        em.getTransaction().commit();

        LocalDateTime timeOfTheFirstEvent = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        em.getTransaction().begin();
        DotaEvent eventFirst = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheFirstEvent,
                75.3,
                12.8,
                21.9);
        em.getTransaction().commit();

        LocalDateTime timeOfTheSecondEvent = sCheck.tryToParseDateFromString("25.06.2015 16:30");

        em.getTransaction().begin();
        DotaEvent eventSecond = daoEventDota.createEvent(teamFirst,
                teamSecond,
                timeOfTheSecondEvent,
                75.3,
                12.8,
                21.9);
        em.getTransaction().commit();

        LocalDateTime date = sCheck.tryToParseDateFromString("25.06.2015");

        em.getTransaction().begin();
        List<DotaEvent> resultList = daoEventDota.chooseAllEventInEnteredDate(date);
        em.getTransaction().commit();

        assertEquals(resultList.size(),1);
    }
}
