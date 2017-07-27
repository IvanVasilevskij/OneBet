package ru.onebet.exampleproject.dao.userdao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.users.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    private final EntityManager em;

    @Autowired
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public ClientImpl createClient(String login, String password) throws EntityExistsException {
        em.getTransaction().begin();
        try {
                ClientImpl client = ClientImpl.builder()
                        .withLogin(login)
                        .withPassword(password)
                        .withBalance(new BigDecimal("0.00"))
                        .build();
                em.persist(client);
                em.getTransaction().commit();
                return client;
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
    public Admin createAdmin(String login, String password) throws EntityExistsException {
        em.getTransaction().begin();
        try {
                Admin admin = Admin.builder()
                        .withLogin(login)
                        .withPassword(password)
                        .withBalance(new BigDecimal("0.00"))
                        .build();

                em.persist(admin);
                em.getTransaction().commit();

                return admin;
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
    public ClientImpl ensureClientForEmitMoneyOperation() {
        try {
            ClientImpl clientForEmitMoneyOperations = findClient(ClientImpl.ClientForEmitMoneyOperations);
            if (clientForEmitMoneyOperations == null) {
                clientForEmitMoneyOperations = createClient(ClientImpl.ClientForEmitMoneyOperations,
                        "null");
            }
            return clientForEmitMoneyOperations;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public Admin ensureRootUser() {
        try {
            Admin root = findAdmin(Admin.RootAdminName);
            if (root == null) {
                root =  createAdmin(Admin.RootAdminName,
                        "4012659172");
            }
            return root;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public User checkPassword(User user, String password) {
        em.getTransaction().begin();
        if (!user.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password");
        em.getTransaction().commit();
        return user;
    }

    @Override
    public void checkBalanceForBet(ClientImpl client, BigDecimal amount) {
        if (client.getBalance().compareTo(amount) < 0) throw new IllegalArgumentException("Client have no balance for this withEvent");
    }

    @Override
    public List<ClientImpl> getAllClients() {
        try {
            return em.createQuery("from ClientImpl", ClientImpl.class).getResultList();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public List<Admin> getAllAdmins() {
        try {
            return em.createQuery("from Admin" , Admin.class).getResultList();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
