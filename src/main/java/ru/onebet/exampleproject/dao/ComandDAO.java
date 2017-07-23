package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.ComandOfDota;

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
        em.getTransaction().begin();
        try {
            if (findComandByComandName(conamdName) == null) {
                ComandOfDota comandOne = ComandOfDota.newBuilder()
                        .comandName(conamdName)
                        .roleMid(roleMid)
                        .roleCarry(roleCarry)
                        .roleHard(roleHard)
                        .roleSupFour(roleSupFour)
                        .roleSupFive(roleSupFive)
                        .build();

                em.persist(comandOne);
                em.getTransaction().commit();
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
