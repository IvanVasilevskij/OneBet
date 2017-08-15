package ru.onebet.exampleproject.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.configurations.TestConfiguration;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class UserDAOImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAOImpl daoUser;

    @Test
    @Transactional
    public void testCreateClientOrAdmin() throws Exception {

        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");

        assertEquals("withClient", daoUser.findClient("withClient").getLogin());
        assertEquals("123456", daoUser.findClient("withClient").getPassword());
        assertEquals(BigDecimal.ZERO.setScale(2), daoUser.findClient("withClient").getBalance());

        assertEquals("admin", daoUser.findAdmin("admin").getLogin());
        assertEquals("654321", daoUser.findAdmin("admin").getPassword());
        assertEquals(BigDecimal.ZERO.setScale(2), daoUser.findAdmin("admin").getBalance());
    }

    @Test
    @Transactional
    public void testFindClientOrAdmin() throws Exception {

        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
        ClientImpl clientFinded = daoUser.findClient("withClient");

        assertEquals(client, clientFinded);

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");

        Admin adminFinded = daoUser.findAdmin("admin");

        assertEquals(admin, adminFinded);

        Admin adminError = daoUser.findAdmin("adminError");

        assertEquals(null, adminError);
    }

    @Test
    @Transactional
    public void testDeleteUserByLogin() throws Exception {

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");

        assertEquals(1, daoUser.getAllClients().size());

        daoUser.deleteUserByLogin("withClient");

        assertEquals(null, daoUser.findClient("withClient"));

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");

        assertEquals(1, daoUser.getAllAdmins().size());

        daoUser.deleteUserByLogin("admin");

        assertEquals(null, daoUser.findAdmin("admin"));


    }

    @Test
    @Transactional
    public void testEnsureClientForEmitMoneyOperation() throws Exception {
        ClientImpl client = daoUser.ensureClientForEmitMoneyOperation();
        ClientImpl clientTrySecond = daoUser.ensureClientForEmitMoneyOperation();

        assertEquals(client, clientTrySecond);

    }

    @Test
    @Transactional
    public void testEnsureRootUser() throws Exception {
        Admin root = daoUser.ensureRootUser();
        Admin rootTrySecond = daoUser.ensureRootUser();

        assertEquals(root, rootTrySecond);
    }

    @Test
    @Transactional
    public void testCheckPassword() throws Exception {
        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");
        assertEquals(client, daoUser.checkPassword(client, "123456"));

    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testCheckBalanceForBet() {
        ClientImpl client = daoUser.createClient(
                "withClient",
                "123456");

        client = ClientImpl.mutator(client)
                .withBalance(new BigDecimal("250.00"))
                .mutate();
        em.refresh(client);

        assertEquals(daoUser.checkBalanceForBet(client, new BigDecimal("251.00")), false);
        assertEquals(daoUser.checkBalanceForBet(client, new BigDecimal("249.00")), true);
    }

    @Test
    @Transactional
    public void testGetAllUsers() throws Exception {
        ClientImpl clientFirst = daoUser.createClient(
                "clientFirst",
                "password");

        assertEquals(1, daoUser.getAllClients().size());

        ClientImpl clientSecond = daoUser.createClient(
                "clientSecond",
                "password");
        assertEquals(2, daoUser.getAllClients().size());

        Admin adminFirst = daoUser.createAdmin(
                "aminFirst",
                "password");

        assertEquals(1, daoUser.getAllAdmins().size());

        Admin adminSecond = daoUser.createAdmin(
                "adminSecond",
                "password");
        assertEquals(2, daoUser.getAllAdmins().size());
    }
}
