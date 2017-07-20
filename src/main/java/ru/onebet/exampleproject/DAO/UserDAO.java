package ru.onebet.exampleproject.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public User createUser(String login,String password, String firstName, String lastName, String email) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findUser(login) == null) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setBalance(0.0);
                user.setEmail(email);

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

    public boolean checkPassword(String login, String password) {
        User user = findUser(login);
        if (user == null) throw new IllegalArgumentException("User not exist");
        return user.getPassword() == password ? true : false;
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
