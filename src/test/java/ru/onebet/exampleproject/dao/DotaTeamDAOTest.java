package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DotaTeamDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO daoTeam;

    @Test
    public void testCreateComand() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        assertEquals("EG", em.find(DotaTeam.class, teamFirst.getId()).getTeamName());
        assertEquals("Sumail", em.find(DotaTeam.class, teamFirst.getId()).getRoleMid());
        assertEquals("Arteezy", em.find(DotaTeam.class, teamFirst.getId()).getRoleCarry());
        assertEquals("Universe", em.find(DotaTeam.class, teamFirst.getId()).getRoleHard());
        assertEquals("Zai", em.find(DotaTeam.class, teamFirst.getId()).getRoleSupFour());
        assertEquals("Crit", em.find(DotaTeam.class, teamFirst.getId()).getRoleSupFive());
    }

    @Test
    public void testFindComandByComandName() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        DotaTeam teamFinded = daoTeam.findTeamByTeamName("EG");

        assertEquals(teamFirst, teamFinded);
    }

    @Test
    public void testDeleteComandByComandName() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        em.getTransaction().begin();

        daoTeam.deleteTeamByTeamName("EG");

        em.getTransaction().commit();

        assertEquals(null, daoTeam.findTeamByTeamName("EG"));
    }

    @Test
    public void testGetAllTeams() throws Exception {
        DotaTeam teamFirst = daoTeam.createTeam(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        assertSame(1, daoTeam.getAllTeams().size());

        DotaTeam teamSecond = daoTeam.createTeam(
                "VP",
                "No[o]ne",
                "Ramzes666",
                "9pasha",
                "Lil",
                "Solo");

        assertSame(2, daoTeam.getAllTeams().size());

    }
}
