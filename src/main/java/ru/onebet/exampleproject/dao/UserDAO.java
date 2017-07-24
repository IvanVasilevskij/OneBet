package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.checks.CheckOperations;
import ru.onebet.exampleproject.model.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class UserDAO {
    private final EntityManager em;

    @Autowired
    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public User findUser(String login) {
        try {
            return em.createNamedQuery(User.FindByLogin, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public User createUser(String login, String password, String firstName, String lastName, String email) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findUser(login) == null) {
                User user = User.newBuilder()
                        .login(login)
                        .password(password)
                        .firstName(firstName)
                        .lastName(lastName)
                        .balance(new BigDecimal("0.0"))
                        .email(email)
                        .build();

                em.persist(user);
                em.getTransaction().commit();
                return user;
            } else return findUser(login);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void deleteUserByLogin(String login) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findUser(login) != null) {
                em.remove(findUser(login));
            }
            em.getTransaction().commit();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
    public User ensureRootUser() {
        try {
            User root = findUser(User.RootUserName);
            if (root == null) {
                root = createUser(User.RootUserName,
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

    public User checkPassword(String login, String password) {
        User user = findUser(login);
        if (user == null) throw new IllegalArgumentException("User didn't exist");
        if (user.getPassword() != password) throw new IllegalArgumentException("Incorrect password");
        return user;
    }

    public BigDecimal checkBalanceForBet(User user, BigDecimal amount) {

        if (user.getBalance().max(amount) == amount) throw new IllegalArgumentException("User have no balance for this bet");

        return user.getBalance();
    }

    public List<User> getAllUser() {
        try {
            List<User> users = em.createQuery("from User").getResultList();
            return users;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
