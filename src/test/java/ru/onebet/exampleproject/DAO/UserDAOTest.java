package ru.onebet.exampleproject.DAO;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.Model.User;
import ru.onebet.exampleproject.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAO daoU;

    @Test
    public void testCreateUser() throws Exception {

        User user = daoU.createUser(
                "root",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        assertEquals("root", em.find(User.class, user.getUserId()).getLogin());
        assertEquals("password", em.find(User.class, user.getUserId()).getPassword());
        assertEquals("Ivan", em.find(User.class, user.getUserId()).getFirstName());
        assertEquals("Vasilevskij", em.find(User.class, user.getUserId()).getLastName());
        assertEquals(0.0, em.find(User.class, user.getUserId()).getBalance(),0.0);
        assertEquals("vasilevskij.ivan@gmail.com", em.find(User.class, user.getUserId()).getEmail());
    }


    @Test
    public void testDeleteUserByLogin() throws Exception {

        User user = daoU.createUser(
                "root",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        daoU.deleteUserByLogin(user.getLogin());

        assertEquals(null, daoU.findUser("root"));
    }

    @Test
    public void testFindUser() throws Exception {

        User user = daoU.createUser(
                "root",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        User userFinded = daoU.findUser("root");

        assertEquals(user,userFinded);
    }

    @Test
    public void testEnsureRootUser() throws Exception {
        User root = daoU.ensureRootUser();
        User rootTwo = daoU.ensureRootUser();

        assertSame(root, rootTwo);
    }
}
