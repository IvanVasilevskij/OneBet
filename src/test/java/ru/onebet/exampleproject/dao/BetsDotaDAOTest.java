package ru.onebet.exampleproject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.BetsDota;
import ru.onebet.exampleproject.configurations.TestConfiguration;
import ru.onebet.exampleproject.model.ComandOfDota;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BetsDotaDAOTest {

    @Autowired
    private BetsDotaDAO daoB;

    @Autowired
    private ComandDotaDAO daoC;

    @Autowired
    private CheckOperations sCheck;


    @Test
    public void testCreateBet() throws Exception {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        ComandOfDota comandTwo = daoC.createComand(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");


        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        BetsDota bet = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        assertEquals("Arteezy", bet.getComandOne().getRoleCarry());
    }

    @Test
    public void testFindBet() throws Exception {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        ComandOfDota comandTwo = daoC.createComand(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        BetsDota bet = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        BetsDota findedBet = daoB.findBet("EG VP 25.05.2015 16:30");

        assertEquals(bet,findedBet);
    }

    @Test
    public void testAllBets() throws Exception {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        ComandOfDota comandTwo = daoC.createComand(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        BetsDota bet = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        List<BetsDota> result = daoB.allBets();

        assertEquals(1,result.size());

        Date timeOfTheGameTwo = sCheck.tryToParseDateFromString("25.05.2015 19:30");

        BetsDota betTwo = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGameTwo,
                75.3,
                12.8,
                21.9);

        List<BetsDota> result2 = daoB.allBets();

        assertEquals(2,result2.size());
    }

    @Test
    public void testMakeSearchingMarkOfDotaBet() {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        ComandOfDota comandTwo = daoC.createComand(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        Date timeOfTheGame = sCheck.tryToParseDateFromString("25.05.2015 16:30");

        BetsDota bet = daoB.createBet(comandOne,
                comandTwo,
                timeOfTheGame,
                75.3,
                12.8,
                21.9);

        String searchMark = daoB.makeSearchingMarkOfDotaBet(comandOne, comandTwo, timeOfTheGame);

        assertEquals("EG VP 25.05.2015 16:30", searchMark);
    }
}
