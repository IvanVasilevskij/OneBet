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
    private DotaTeamDAO dotaTeamDAO;

    @Test
    public void testCreateTeam() throws Exception {
        DotaTeam teamFirst = dotaTeamDAO.createTeam("EG");

        assertEquals("EG", em.find(DotaTeam.class, teamFirst.getId()).getTeamName());
}

    @Test
    public void testFindTeamByTeamName() throws Exception {
        DotaTeam teamFirst = dotaTeamDAO.createTeam(
                "EG");

        DotaTeam teamFinded = dotaTeamDAO.findTeamByTeamName("EG");

        assertEquals(teamFirst, teamFinded);
    }

    @Test
    public void testDeleteTeamByTeamName() throws Exception {
        DotaTeam teamFirst = dotaTeamDAO.createTeam("EG");

        em.getTransaction().begin();

        dotaTeamDAO.deleteTeamByTeamName("EG");

        em.getTransaction().commit();

        assertEquals(null, dotaTeamDAO.findTeamByTeamName("EG"));
    }

    @Test
    public void testGetAllTeams() throws Exception {
        DotaTeam teamFirst = dotaTeamDAO.createTeam("EG");

        assertSame(1, dotaTeamDAO.getAllTeams().size());

        DotaTeam teamSecond = dotaTeamDAO.createTeam("VP");

        assertSame(2, dotaTeamDAO.getAllTeams().size());

    }
}
