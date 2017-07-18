package ru.onebet.exampleproject.DAO;


import ru.onebet.exampleproject.Model.ComandOfDota;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class AddNewComand {
    private final EntityManager em;

    public AddNewComand(EntityManager em) {
        this.em = em;
    }

    public ComandOfDota findComandByComandName(String comandName) {
        try {
            return em.createNamedQuery(ComandOfDota.FindByLogin, ComandOfDota.class)
                    .setParameter("comandName", comandName)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public String createUser(String conamdName, String roleMid, String roleCarry, String roleHard, String roleSupFour, String roleSupFive) throws EntityExistsException {
        try {
            if (findComandByComandName(conamdName) == null) {
                ComandOfDota comandOne = new ComandOfDota();
                comandOne.setComandName(conamdName);
                comandOne.setRoleMid(roleMid);
                comandOne.setRoleCarry(roleCarry);
                comandOne.setRoleHard(roleHard);
                comandOne.setRoleSupFour(roleSupFour);
                comandOne.setRoleSupFive(roleSupFive);

                em.persist(comandOne);

                return "Команда успешно добавлена";
            } else return "Команда " + conamdName + "уже существует";
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

}
