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

    public String createUser(String login, String firstName, String lastName, String email) throws EntityExistsException {
        try {
            if (findUser(login) == null) {
                User user = new User();
                user.setLogin(login);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setBalance(0.0);
                user.setEmail(email);

                em.persist(user);

                return "Пользователь успешно создан";
            } else return "Пользователь с таким login:" + login + " уже существует";
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
