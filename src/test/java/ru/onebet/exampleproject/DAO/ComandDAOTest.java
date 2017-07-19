package ru.onebet.exampleproject.DAO;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.onebet.exampleproject.Model.ComandOfDota;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class ComandDAOTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("postgres");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    public void testCreateComand() throws Exception {
        em.getTransaction().begin();
        ComandDAO dao = new ComandDAO(em);
        ComandOfDota comandOne = dao.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");
        em.persist(comandOne);
        em.getTransaction().commit();

        assertEquals("EG", em.find(ComandOfDota.class, comandOne.getId()).getComandName());
        assertEquals("Sumail", em.find(ComandOfDota.class, comandOne.getId()).getRoleMid());
        assertEquals("Arteezy", em.find(ComandOfDota.class, comandOne.getId()).getRoleCarry());
        assertEquals("Universe", em.find(ComandOfDota.class, comandOne.getId()).getRoleHard());
        assertEquals("Zai", em.find(ComandOfDota.class, comandOne.getId()).getRoleSupFour());
        assertEquals("Crit", em.find(ComandOfDota.class, comandOne.getId()).getRoleSupFive());
    }

    @Test
    public void testFindComandByComandName() throws Exception {
        em.getTransaction().begin();
        ComandDAO dao = new ComandDAO(em);
        ComandOfDota comandOne = dao.createComand(
                "EG",
                "Sumail",
                "Arteezy",
                "Universe",
                "Zai",
                "Crit");
        em.persist(comandOne);
        ComandOfDota comandFinded = dao.findComandByComandName("EG");
        em.getTransaction().commit();

        assertEquals(comandOne,comandFinded);
    }
}
