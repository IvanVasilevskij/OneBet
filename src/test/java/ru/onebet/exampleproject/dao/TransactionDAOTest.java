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
public class TransactionDAOTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserDAOImpl daoUser;

    @Autowired
    private TransactionDAO daoTransaction;

    @Test
    @Transactional
    public void testEmitMoneyAndTransactionsList() throws Exception {

        Admin root = daoUser.ensureRootUser();
        ClientImpl clientForEmitMoneyOperation = daoUser.ensureClientForEmitMoneyOperation();

        daoTransaction.emitMoney(root, clientForEmitMoneyOperation, new BigDecimal("500.00"));

        assertEquals(new BigDecimal("500.00"), root.getBalance());
        assertEquals(new BigDecimal("500.00"), root.getTransactions().get(0).getAmount());

        // тест transactionsList
        assertEquals(1, daoTransaction.transactionList().size());
        // конец данного теста
    }

    @Test
    @Transactional
    public void testSendMoney() throws Exception {

        Admin root = daoUser.ensureRootUser();

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");

        daoTransaction.sendMoney(client, root, new BigDecimal("100.00"));

        assertEquals(new BigDecimal("-100.00"), client.getBalance());
        assertEquals(new BigDecimal("100.00"), root.getBalance());
        assertEquals(new BigDecimal("100.00"), client.getTransactions().get(0).getAmount());
    }

    @Test
    @Transactional
    public void testReciveMoney() throws Exception {

        Admin root = daoUser.ensureRootUser();

        ClientImpl client = daoUser.createClient(
                "withClient",
                "password");

        ClientImpl clientForEmitMoneyOperation = daoUser.ensureClientForEmitMoneyOperation();
        daoTransaction.reciveMoney(client, root, new BigDecimal("100.00"));

        assertEquals(new BigDecimal("100.00"), client.getBalance());
        assertEquals(new BigDecimal("-100.00"), root.getBalance());
        assertEquals(new BigDecimal("100.00"), client.getTransactions().get(0).getAmount());

        //add other test
        assertEquals(1, daoTransaction.transactionsListOfClientOrAdmin(client.getLogin()).size());
        //end
    }

    @Test
    @Transactional
    public void testCheckBalanceForPayoutPrize() throws Exception {

        Admin admin = daoUser.createAdmin(
                "admin",
                "654321");
        ClientImpl clientForEmitMoneyOperation = daoUser.ensureClientForEmitMoneyOperation();

        daoTransaction.emitMoney(admin, clientForEmitMoneyOperation, new BigDecimal("500.00"));

        BigDecimal resultTrue =  daoTransaction.checkBalanceForPayoutPrize(admin, new BigDecimal("200.00"));

        assertEquals(BigDecimal.ZERO, resultTrue);

        BigDecimal resultFalse =  daoTransaction.checkBalanceForPayoutPrize(admin, new BigDecimal("550.00"));

        assertEquals(new BigDecimal("50.00"), resultFalse);
    }
}
