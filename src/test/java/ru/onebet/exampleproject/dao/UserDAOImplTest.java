package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAOImpl daoUser;

    @Test
    public void testCreateClientOrAdmin() throws Exception {

        ClientImpl client = daoUser.createClient(
                "client",
                "123456",
                "vasilevskij.ivan@gmail.com");

        assertEquals("client", em.find(ClientImpl.class, client.getId()).getLogin());
        assertEquals("123456", em.find(ClientImpl.class, client.getId()).getPassword());
        assertEquals(new BigDecimal("0.00"), em.find(ClientImpl.class, client.getId()).getBalance());
        assertEquals("vasilevskij.ivan@gmail.com", em.find(ClientImpl.class, client.getId()).getEmail());

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321",
                "vasilevskij.ivan@gmail.com");

        assertEquals("admin", em.find(Admin.class, admin.getId()).getLogin());
        assertEquals("654321", em.find(Admin.class, admin.getId()).getPassword());
        assertEquals(new BigDecimal("0.00"), em.find(Admin.class, admin.getId()).getBalance());
        assertEquals("vasilevskij.ivan@gmail.com", em.find(Admin.class, admin.getId()).getEmail());
    }

    @Test
    public void testFindClientOrAdmin() throws Exception {

        ClientImpl client = daoUser.createClient(
                "client",
                "123456",
                "vasilevskij.ivan@gmail.com");

        ClientImpl clientFinded = daoUser.findClient("client");

        assertEquals(client, clientFinded);

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321",
                "vasilevskij.ivan@gmail.com");

        Admin adminFinded = daoUser.findAdmin("admin");

        assertEquals(admin, adminFinded);
    }

    @Test
    public void testDeleteUserByLogin() throws Exception {

        ClientImpl client = daoUser.createClient(
                "client",
                "password",
                "vasilevskij.ivan@gmail.com");

        assertEquals(1,em.createQuery("from ClientImpl").getResultList().size());

        daoUser.deleteUserByLogin("client");

        assertEquals(null, daoUser.findClient("client"));

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321",
                "vasilevskij.ivan@gmail.com");

        assertEquals(1,em.createQuery("from Admin ").getResultList().size());

        daoUser.deleteUserByLogin("admin");

        assertEquals(null, daoUser.findAdmin("admin"));


    }

    @Test
    public void testEnsureClientForEmitMoneyOperation() throws Exception {
        ClientImpl client = daoUser.ensureClientForEmitMoneyOperation();
        ClientImpl clientTrySecond = daoUser.ensureClientForEmitMoneyOperation();

        assertSame(client, clientTrySecond);
    }

    @Test
    public void testEnsureRootUser() throws Exception {
        Admin root = daoUser.ensureRootUser();
        Admin rootTrySecond = daoUser.ensureRootUser();

        assertSame(root, rootTrySecond);
    }

    @Test
    public void testCheckPassword() throws Exception {
        ClientImpl client = daoUser.createClient(
                "client",
                "123456",
                "vasilevskij.ivan@gmail.com");

        assertEquals(client, daoUser.checkPassword("client", "123456"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckBalanceForBet() {
        ClientImpl client = daoUser.createClient(
                "client",
                "123456",
                "vasilevskij.ivan@gmail.com");

        em.getTransaction().begin();

        client.Mutate(client)
                .balance(new BigDecimal("250.00"))
                .mutate();

        em.persist(client);
        em.getTransaction().commit();

        daoUser.checkBalanceForBet(client, new BigDecimal("251.00"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        ClientImpl clientFirst = daoUser.createClient(
                "clientFirst",
                "password",
                "vasilevskij.ivan@gmail.com");

        assertEquals(1, daoUser.getAllClients().size());

        ClientImpl clientSecond = daoUser.createClient(
                "clientSecond",
                "password",
                "vasilevskij.ivan@gmail.com");

        assertEquals(2, daoUser.getAllClients().size());

        Admin adminFirst = daoUser.createAdmin(
                "aminFirst",
                "password",
                "vasilevskij.ivan@gmail.com");

        assertEquals(1, daoUser.getAllAdmins().size());

        Admin adminSecond = daoUser.createAdmin(
                "adminSecond",
                "password",
                "vasilevskij.ivan@gmail.com");

        assertEquals(2, daoUser.getAllAdmins().size());
    }
}
