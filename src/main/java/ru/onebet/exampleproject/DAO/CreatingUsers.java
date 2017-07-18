package ru.onebet.exampleproject.DAO;


import ru.onebet.exampleproject.Model.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class CreatingUsers {
    private final EntityManager em;

    public CreatingUsers(EntityManager em) {
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

    public User createUser(String login, String firstName, String lastName) throws EntityExistsException {
        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBalance(0.0);

        em.persist(user);

        return user;
    }

    public User ensureRootUser() {
        em.getTransaction().begin();

        try {
            User root = findUser(User.RootUserName);
            if (root == null) {
                root = createUser(User.RootUserName, "root", "root");
            }
            em.getTransaction().commit();

            return root;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
