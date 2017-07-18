package ru.onebet.exampleproject;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class CreatingUsers {
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
    public void testUser() throws Exception {
        em.getTransaction().begin();

        User user = new User();
        user.setLogin("root");
        user.setFirstName("Ivan");
        user.setLastName("Vasilevskij");
        user.setBalance(1.0);
        user.setEmail("vasilevskij.ivan@gmail.com");

        em.persist(user);

        em.getTransaction().commit();

        assertEquals("root", em.find(User.class, user.getUserId()).getLogin());
        assertEquals("Ivan", em.find(User.class, user.getUserId()).getFirstName());
        assertEquals("Vasilevskij", em.find(User.class, user.getUserId()).getLastName());
        assertEquals(1.0, em.find(User.class, user.getUserId()).getBalance(),0.0);
        assertEquals("vasilevskij.ivan@gmail.com", em.find(User.class, user.getUserId()).getEmail());
    }
}
