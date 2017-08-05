package ru.onebet.exampleproject.dao.teamdao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class DotaTeamDAO implements TeamDAO <DotaTeam>{

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

    @Override
    public DotaTeam createTeam(String teamName) throws EntityExistsException {
        try {
                DotaTeam team = DotaTeam.Builder()
                        .withTeamName(teamName)
                        .build();
                em.persist(team);
                return team;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public void deleteTeamByTeamName(String teamName) {
        try {
                em.remove(findTeamByTeamName(teamName));
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public List<DotaTeam> getAllTeams() {
        try {
            return em.createQuery("from DotaTeam", DotaTeam.class).getResultList();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
