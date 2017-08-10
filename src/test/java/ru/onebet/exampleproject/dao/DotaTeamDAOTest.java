package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.model.team.DotaTeam;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class DotaTeamDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private DotaTeamDAO dotaTeamDAO;

    @Test
    @Transactional
    public void testCreateTeam() throws Exception {
//        em.getTransaction().begin();
        DotaTeam teamFirst = dotaTeamDAO.createTeam("EG");
//        em.getTransaction().commit();

        assertEquals("EG", em.find(DotaTeam.class, teamFirst.getId()).getTeamName());
}

    @Test
    public void testFindTeamByTeamName() throws Exception {
        em.getTransaction().begin();
        DotaTeam teamFirst = dotaTeamDAO.createTeam(
                "EG");
        em.getTransaction().commit();

        em.getTransaction().begin();
        DotaTeam teamFinded = dotaTeamDAO.findTeamByTeamName("EG");
        em.getTransaction().commit();

        assertEquals(teamFirst, teamFinded);
    }

    @Test
    public void testDeleteTeamByTeamName() throws Exception {

        em.getTransaction().begin();
        DotaTeam teamFirst = dotaTeamDAO.createTeam("EG");
        em.getTransaction().commit();

        em.getTransaction().begin();
        dotaTeamDAO.deleteTeamByTeamName("EG");
        em.getTransaction().commit();

        assertEquals(null, dotaTeamDAO.findTeamByTeamName("EG"));
    }

    @Test
    public void testGetAllTeams() throws Exception {
        em.getTransaction().begin();
        DotaTeam teamFirst = dotaTeamDAO.createTeam("EG");
        em.getTransaction().commit();

        assertSame(1, dotaTeamDAO.getAllTeams().size());

        em.getTransaction().begin();
        DotaTeam teamSecond = dotaTeamDAO.createTeam("VP");
        em.getTransaction().commit();

        assertSame(2, dotaTeamDAO.getAllTeams().size());

    }
}
