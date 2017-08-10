package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
@WebAppConfiguration
@EnableWebSecurity
public class UserDAOImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAOImpl daoUser;

    @Test
    public void testCreateClientOrAdmin() throws Exception {

        em.getTransaction().begin();
        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");

        em.getTransaction().commit();
        assertEquals("withClient", em.find(ClientImpl.class, client.getId()).getLogin());
        assertEquals("123456", em.find(ClientImpl.class, client.getId()).getPassword());
        assertEquals(BigDecimal.ZERO, em.find(ClientImpl.class, client.getId()).getBalance());

        em.getTransaction().begin();
        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");
        em.getTransaction().commit();

        assertEquals("admin", em.find(Admin.class, admin.getId()).getLogin());
        assertEquals("654321", em.find(Admin.class, admin.getId()).getPassword());
        assertEquals(BigDecimal.ZERO, em.find(Admin.class, admin.getId()).getBalance());
    }

    @Test
    public void testFindClientOrAdmin() throws Exception {

        em.getTransaction().begin();
        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
        em.getTransaction().commit();

        ClientImpl clientFinded = daoUser.findClient("withClient");

        assertEquals(client, clientFinded);

        em.getTransaction().begin();
        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");

        em.getTransaction().commit();
        Admin adminFinded = daoUser.findAdmin("admin");

        assertEquals(admin, adminFinded);

        Admin adminError = daoUser.findAdmin("adminError");

        assertEquals(null, adminError);


    }

    @Test
    public void testDeleteUserByLogin() throws Exception {

        em.getTransaction().begin();
        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");
        em.getTransaction().commit();

        assertEquals(1,em.createQuery("from ClientImpl", ClientImpl.class).getResultList().size());

        em.getTransaction().begin();
        daoUser.deleteUserByLogin("withClient");
        em.getTransaction().commit();

        assertEquals(null, daoUser.findClient("withClient"));

        em.getTransaction().begin();
        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");
        em.getTransaction().commit();

        assertEquals(1,em.createQuery("from Admin ", Admin.class).getResultList().size());

        em.getTransaction().begin();
        daoUser.deleteUserByLogin("admin");
        em.getTransaction().commit();
        assertEquals(null, daoUser.findAdmin("admin"));


    }

    @Test
    public void testEnsureClientForEmitMoneyOperation() throws Exception {
        em.getTransaction().begin();
        ClientImpl client = daoUser.ensureClientForEmitMoneyOperation();
        em.getTransaction().commit();
        em.getTransaction().begin();
        ClientImpl clientTrySecond = daoUser.ensureClientForEmitMoneyOperation();
        em.getTransaction().commit();
        assertSame(client, clientTrySecond);
    }

    @Test
    public void testEnsureRootUser() throws Exception {
        em.getTransaction().begin();
        Admin root = daoUser.ensureRootUser();
        em.getTransaction().commit();
        em.getTransaction().begin();
        Admin rootTrySecond = daoUser.ensureRootUser();
        em.getTransaction().commit();
        assertSame(root, rootTrySecond);
    }

    @Test
    public void testCheckPassword() throws Exception {
        em.getTransaction().begin();
        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
        em.getTransaction().commit();
        assertEquals(client, daoUser.checkPassword(client, "123456"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckBalanceForBet() {
        em.getTransaction().begin();
        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
        em.getTransaction().commit();
        em.getTransaction().begin();

        client = ClientImpl.mutator(client)
                .withBalance(new BigDecimal("250.00"))
                .mutate();

        em.persist(client);
        em.getTransaction().commit();

        daoUser.checkBalanceForBet(client, new BigDecimal("251.00"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        em.getTransaction().begin();
        ClientImpl clientFirst = daoUser.createClient(
                "clientFirst",
                "password");
        em.getTransaction().commit();

        assertEquals(1, daoUser.getAllClients().size());

        em.getTransaction().begin();
        ClientImpl clientSecond = daoUser.createClient(
                "clientSecond",
                "password");
        em.getTransaction().commit();
        assertEquals(2, daoUser.getAllClients().size());

        em.getTransaction().begin();
        Admin adminFirst = daoUser.createAdmin(
                "aminFirst",
                "password");
        em.getTransaction().commit();
        assertEquals(1, daoUser.getAllAdmins().size());

        em.getTransaction().begin();
        Admin adminSecond = daoUser.createAdmin(
                "adminSecond",
                "password");
        em.getTransaction().commit();
        assertEquals(2, daoUser.getAllAdmins().size());
    }
}
