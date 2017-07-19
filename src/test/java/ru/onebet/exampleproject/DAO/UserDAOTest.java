package ru.onebet.exampleproject.DAO;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {
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
    public void testCreateUser() throws Exception {
        em.getTransaction().begin();

        User user = new UserDAO(em).createUser("root","Ivan","Vasilevskij", "vasilevskij.ivan@gmail.com");

        em.persist(user);

        em.getTransaction().commit();

        assertEquals("root", em.find(User.class, user.getUserId()).getLogin());
        assertEquals("Ivan", em.find(User.class, user.getUserId()).getFirstName());
        assertEquals("Vasilevskij", em.find(User.class, user.getUserId()).getLastName());
        assertEquals(0.0, em.find(User.class, user.getUserId()).getBalance(),0.0);
        assertEquals("vasilevskij.ivan@gmail.com", em.find(User.class, user.getUserId()).getEmail());
    }


    @Test
    public void testDeleteUserByLogin() throws Exception {
        em.getTransaction().begin();
        User user = new UserDAO(em).createUser(
                "root",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.persist(user);

        UserDAO dao = new UserDAO(em);
        dao.deleteUserByLogin(user.getLogin());

        em.getTransaction().commit();
        assertEquals(null, dao.findUser("root"));
    }

    @Test
    public void testFindUser() throws Exception {
        em.getTransaction().begin();
        User user = new UserDAO(em).createUser(
                "root",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.persist(user);

        UserDAO dao = new UserDAO(em);
        User userFinded = dao.findUser("root");
        em.getTransaction().commit();

        assertEquals(user,userFinded);
    }
}
