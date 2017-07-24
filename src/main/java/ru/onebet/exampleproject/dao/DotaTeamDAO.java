package ru.onebet.exampleproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.DotaTeam;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Service
public class DotaTeamDAO {
    private final EntityManager em;

    @Autowired
    public DotaTeamDAO(EntityManager em) {
        this.em = em;
    }

    public DotaTeam findComandByTeamName(String teamName) {
        try {
            return em.createNamedQuery(DotaTeam.FindByLogin, DotaTeam.class)
                    .setParameter("teamName", teamName)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public DotaTeam createTeam(String teamName, String roleMid, String roleCarry, String roleHard, String roleSupFour, String roleSupFive) throws EntityExistsException {
        em.getTransaction().begin();
        try {
            if (findComandByTeamName(teamName) == null) {
                DotaTeam comandOne = DotaTeam.newBuilder()
                        .comandName(teamName)
                        .roleMid(roleMid)
                        .roleCarry(roleCarry)
                        .roleHard(roleHard)
                        .roleSupFour(roleSupFour)
                        .roleSupFive(roleSupFive)
                        .build();

                em.persist(comandOne);
                em.getTransaction().commit();
                return comandOne;
            } else return findComandByTeamName(teamName);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void deleteComandByComandName(String comandName) {
        try {
            if (findComandByTeamName(comandName) != null) {
                em.remove(findComandByTeamName(comandName));
            }
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

}
