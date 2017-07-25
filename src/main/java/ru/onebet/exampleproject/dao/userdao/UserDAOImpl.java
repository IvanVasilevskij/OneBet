package ru.onebet.exampleproject.dao.userdao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.TransactionDAO;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class UserDAOImpl implements UserDAO{
    private final EntityManager em;
    private TransactionDAO daoTransaction;

    @Autowired
    public UserDAOImpl(EntityManager em,
                       TransactionDAO daoTransaction) {
        this.em = em;
        this.daoTransaction = daoTransaction;
    }

    @Override
    public ClientImpl createClient(String login, String password, String firstName, String lastName, String email) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findClient(login).equals(null) && findAdmin(login).equals(null)) {
                ClientImpl client = ClientImpl.Builder()
                        .login(login)
                        .password(password)
                        .firstName(firstName)
                        .lastName(lastName)
                        .balance(new BigDecimal("0.0"))
                        .email(email)
                        .build();

                em.persist(client);
                em.getTransaction().commit();

                return client;
            } else return findClient(login);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public ClientImpl findClient(String login) {
        try {
            return em.createNamedQuery(ClientImpl.FindByLogin, ClientImpl.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    @Override
    public Admin createAdmin(String login, String password, String firstName, String lastName, String email) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findClient(login).equals(null) && findAdmin(login).equals(null)) {
                Admin admin = Admin.Builder()
                        .login(login)
                        .password(password)
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .build();

                em.persist(admin);
                em.getTransaction().commit();

                return admin;
            } else return findAdmin(login);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public Admin findAdmin(String login) {
        try {
            return em.createNamedQuery(Admin.FindByLogin, Admin.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    @Override
    public void deleteUserByLogin(String login) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findClient(login) != null) {
                em.remove(findClient(login));
            }
            else if(findAdmin((login)) != null) {
                em.remove(findAdmin(login));
            }
            em.getTransaction().commit();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public Admin ensureRootUser() {
        try {
            Admin root = findAdmin(Admin.RootAdminName);
            if (root.equals(null)) {
                root =  createAdmin(Admin.RootAdminName,
                        "password",
                        "Ivan",
                        "Vasilevskij",
                        "vasilevskij.ivan@gmail.com");
            }
            return root;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public ClientImpl checkPassword(String login, String password) {
        em.getTransaction().begin();

        ClientImpl client = findClient(login);
        if (client == null) throw new IllegalArgumentException("Client didn't exist");
        if (!client.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password");

        em.getTransaction().commit();
        return client;
    }

    @Override
    public void checkBalanceForBet(ClientImpl client, BigDecimal amount) {
        if (client.getBalance().compareTo(amount) < 0) throw new IllegalArgumentException("Client have no balance for this event");
    }

    @Override
    public void checkBalanceForPayoutPrize(Admin admin, BigDecimal amount) {
        if (admin.getBalance().compareTo(amount) < 0) {
            BigDecimal shortage = amount.subtract(admin.getBalance());
            daoTransaction.emitMoney(admin, shortage);
        }
    }

    @Override
    public List<ClientImpl> getAllClients() {
        try {
            List<ClientImpl> clients = em.createQuery("from ClientImpl").getResultList();
            return clients;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public List<Admin> getAllAdmins() {
        try {
            List<Admin> admin = em.createQuery("from Admin ").getResultList();
            return admin;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
