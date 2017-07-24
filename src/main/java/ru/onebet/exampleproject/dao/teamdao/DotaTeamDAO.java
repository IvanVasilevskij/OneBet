package ru.onebet.exampleproject.dao.teamdao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Service
public class DotaTeamDAO implements TeamDAO{
    private final EntityManager em;

    @Autowired
    public DotaTeamDAO(EntityManager em) {
        this.em = em;
    }

    public DotaTeam findTeamByTeamName(String teamName) {
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
            if (findTeamByTeamName(teamName) == null) {
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
            } else return findTeamByTeamName(teamName);
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void deleteTeamByTeamName(String teamName) {
        try {
            if (findTeamByTeamName(teamName) != null) {
                em.remove(findTeamByTeamName(teamName));
            }
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
