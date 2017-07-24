package ru.onebet.exampleproject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.DotaBets;
import ru.onebet.exampleproject.configurations.TestConfiguration;
import ru.onebet.exampleproject.model.DotaTeam;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DotaBetsDAOTest {

    @Autowired
    private DotaBetsDAO daoB;

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

        DotaBets bet = daoB.createBet(teamOne,
                teamTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        assertEquals("Arteezy", bet.getTeamOne().getRoleCarry());
    }

    @Test
    public void testFindBet() throws Exception {
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

        DotaBets findedBet = daoB.findBet("EG VP 25.05.2015 16:30");

        assertEquals(bet,findedBet);
    }

    @Test
    public void testAllBets() throws Exception {
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

        List<DotaBets> result = daoB.allBets();

        assertEquals(1,result.size());

        Date timeOfTheGameTwo = sCheck.tryToParseDateFromString("25.05.2015 19:30");

        DotaBets betTwo = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGameTwo,
                75.3,
                12.8,
                21.9);

        List<DotaBets> result2 = daoB.allBets();

        assertEquals(2,result2.size());
    }

    @Test
    public void testMakeSearchingMarkOfDotaBet() {
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

        String searchMark = daoB.makeSearchingMarkOfDotaBet(comandOne, comandTwo, timeOfTheGame);

        assertEquals("EG VP 25.05.2015 16:30", searchMark);
    }
}
