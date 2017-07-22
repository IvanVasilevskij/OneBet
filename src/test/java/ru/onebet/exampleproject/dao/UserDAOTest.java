package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.model.User;
import ru.onebet.exampleproject.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

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
        assertEquals(new BigDecimal("0.0"), em.find(User.class, user.getUserId()).getBalance());
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

        assertEquals(user, userFinded);
    }

    @Test
    public void testEnsureRootUser() throws Exception {
        User root = daoU.ensureRootUser();
        User rootTwo = daoU.ensureRootUser();

        assertSame(root, rootTwo);
    }

    @Test
    public void testCheckPassword() throws Exception {
        User userOne = daoU.createUser(
                "userOne",
                "123456",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        assertEquals(userOne, daoU.checkPassword("userOne", "123456"));

    }

    @Test
    public void testGetAllUsers() throws Exception {
        User userOne = daoU.createUser(
                "userOne",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");
        User userTwo = daoU.createUser(
                "userTwo",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");
        List<User> result = daoU.getAllUser();
        assertEquals(2, result.size());
    }

    @Test
    public void testCheckBalanceForBet() throws Exception {
        User userOne = daoU.createUser(
                "userOne",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        userOne.setBalance(new BigDecimal("250.00"));

        em.persist(userOne);
        em.getTransaction().commit();

        assertEquals(new BigDecimal("250.00"), daoU.checkBalanceForBet("userOne",
                "password",
                "150.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckBalanceForBetWithException() throws Exception {
        User userOne = daoU.createUser(
                "userOne",
                "password",
                "Ivan",
                "Vasilevskij",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        userOne.setBalance(new BigDecimal("250.00"));

        em.persist(userOne);
        em.getTransaction().commit();

        BigDecimal bd = daoU.checkBalanceForBet("userOne",
                "password",
                "500.00");
    }
}
