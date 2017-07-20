package ru.onebet.exampleproject.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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

    public User createUser(String login, String firstName, String lastName, String email) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findUser(login) == null) {
                User user = new User();
                user.setLogin(login);
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
}
