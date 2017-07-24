package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.model.DotaTeam;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DotaTeamDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO daoC;

    @Test
    public void testCreateComand() throws Exception {
        DotaTeam comandOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        assertEquals("EG", em.find(DotaTeam.class, comandOne.getId()).getTeamName());
        assertEquals("Sumail", em.find(DotaTeam.class, comandOne.getId()).getRoleMid());
        assertEquals("Arteezy", em.find(DotaTeam.class, comandOne.getId()).getRoleCarry());
        assertEquals("Universe", em.find(DotaTeam.class, comandOne.getId()).getRoleHard());
        assertEquals("Zai", em.find(DotaTeam.class, comandOne.getId()).getRoleSupFour());
        assertEquals("Crit", em.find(DotaTeam.class, comandOne.getId()).getRoleSupFive());
    }

    @Test
    public void testFindComandByComandName() throws Exception {
        DotaTeam comandOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");
        DotaTeam comandFinded = daoC.findComandByTeamName("EG");

        assertEquals(comandOne, comandFinded);
    }

    @Test
    public void testDeleteComandByComandName() throws Exception {
        DotaTeam comandOne = daoC.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        em.getTransaction().begin();

        daoC.deleteComandByComandName("EG");

        em.getTransaction().commit();

        assertEquals(null, daoC.findComandByTeamName("EG"));
    }
}
