package ru.onebet.exampleproject.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.ComandOfDota;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Service
public class ComandDAO {
    private final EntityManager em;

    @Autowired
    public ComandDAO(EntityManager em) {
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

    public ComandOfDota createComand(String conamdName, String roleMid, String roleCarry, String roleHard, String roleSupFour, String roleSupFive) throws EntityExistsException {
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

                return comandOne;
            } else return findComandByComandName(conamdName);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void deleteComandByComandName(String comandName) {
        try {
            if (findComandByComandName(comandName) != null) {
                em.remove(findComandByComandName(comandName));
            }
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

}
