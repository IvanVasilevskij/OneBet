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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DotaEventsDAOTest {

    @Autowired
    private DotaEventsDAO daoB;

    @Autowired
    private DotaTeamDAO daoC;

    @Autowired
    private CheckOperations sCheck;


    @Test
    public void testCreateBet() throws Exception {
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

        assertEquals("Arteezy", event.getTeamOne().getRoleCarry());
    }

    @Test
    public void testFindBet() throws Exception {
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

        DotaEvent findedEvent = daoB.findEvent("EG VP 25.05.2015 16:30");

        assertEquals(event,findedEvent);
    }

    @Test
    public void testAllBets() throws Exception {
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

        List<DotaEvent> result = daoB.allEvents();

        assertEquals(1,result.size());

        Date timeOfTheGameTwo = sCheck.tryToParseDateFromString("25.05.2015 19:30");

        DotaEvent eventTwo = daoB.createEvent("EG",
                "VP",
                timeOfTheGameTwo,
                75.3,
                12.8,
                21.9);

        List<DotaEvent> result2 = daoB.allEvents();

        assertEquals(2,result2.size());
    }

    @Test
    public void testMakeSearchingMarkOfDotaBet() {
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

        String searchMark = daoB.createSearchingMark(event.getTeamOne().getTeamName(),
                event.getTeamTwo().getTeamName(),
                timeOfTheGame);

        assertEquals("EG VP 25.05.2015 16:30", searchMark);
    }
}
