package ru.onebet.exampleproject.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.model.Bets;
import ru.onebet.exampleproject.model.ComandOfDota;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BetsDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BetsDAO daoB;

    @Autowired
    private ComandDAO daoC;

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

        Bets bet = daoB.createBet("EG",
                "VP",
                "2017.09.15 16:30",
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

        Bets bet = daoB.createBet("EG",
                "VP",
                "2017.09.15 16:30",
                75.3,
                12.8,
                21.9);

        Bets findedBet = daoB.findBet("EGVP2017.09.15 16:30");

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

        Bets bet = daoB.createBet("EG",
                "VP",
                "2017.09.15 16:30",
                75.3,
                12.8,
                21.9);
        List<Bets> result = daoB.allBets();

        assertEquals(1,result.size());

        Bets bet2 = daoB.createBet("EG",
                "VP",
                "2017.09.15 19:30",
                75.3,
                12.8,
                21.9);
        List<Bets> result2 = daoB.allBets();

        assertEquals(2,result2.size());


    }
}
