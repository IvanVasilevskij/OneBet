package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.model.ComandOfDota;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ComandDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAO daoU;

    @Autowired
    private ComandDAO daoC;

    @Test
    public void testCreateComand() throws Exception {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        assertEquals("EG", em.find(ComandOfDota.class, comandOne.getId()).getComandName());
        assertEquals("Sumail", em.find(ComandOfDota.class, comandOne.getId()).getRoleMid());
        assertEquals("Arteezy", em.find(ComandOfDota.class, comandOne.getId()).getRoleCarry());
        assertEquals("Universe", em.find(ComandOfDota.class, comandOne.getId()).getRoleHard());
        assertEquals("Zai", em.find(ComandOfDota.class, comandOne.getId()).getRoleSupFour());
        assertEquals("Crit", em.find(ComandOfDota.class, comandOne.getId()).getRoleSupFive());
    }

    @Test
    public void testFindComandByComandName() throws Exception {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");
        ComandOfDota comandFinded = daoC.findComandByComandName("EG");

        assertEquals(comandOne, comandFinded);
    }

    @Test
    public void testDeleteComandByComandName() throws Exception {
        ComandOfDota comandOne = daoC.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");

        em.getTransaction().begin();

        daoC.deleteComandByComandName("EG");

        em.getTransaction().commit();

        assertEquals(null, daoC.findComandByComandName("EG"));
    }
}
