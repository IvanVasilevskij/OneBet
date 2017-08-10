package ru.onebet.exampleproject.dao.teamdao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DotaTeamDAO implements TeamDAO <DotaTeam>{

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public DotaTeamDAO(EntityManager em) {
        this.em = em;
    }

    public DotaTeam findTeamByTeamName(String teamName) {
            return em.createNamedQuery(DotaTeam.FindByTeamName, DotaTeam.class)
                    .setParameter("teamName", teamName)
                    .getSingleResult();
    }

    @Override
    @Transactional
    public DotaTeam createTeam(String teamName) throws EntityExistsException {
//        try {
                DotaTeam team = DotaTeam.Builder()
                        .withTeamName(teamName)
                        .build();
                em.persist(team);
                return team;
//        } catch (Throwable t) {
//            em.getTransaction().rollback();
//            throw new IllegalStateException(t);
//        }
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
            return em.createQuery("from DotaTeam", DotaTeam.class).getResultList();
    }

    public DotaTeam updateDOtaTeam(DotaTeam teamF,
                               String roleMid,
                               String roleCarry,
                               String roleHard,
                               String roleSupFour,
                               String roleSupFive) {
        teamF = DotaTeam.mutator(teamF)
                .withRoleMid(roleMid.equals("") ? teamF.getRoleMid() : roleMid)
                .withRoleCarry(roleCarry.equals("") ? teamF.getRoleCarry() : roleCarry)
                .withRoleHard(roleHard.equals("") ? teamF.getRoleHard() : roleHard)
                .withRoleSupFour(roleSupFour.equals("") ? teamF.getRoleSupFour() : roleSupFour)
                .withRoleSupFive(roleSupFive.equals("") ? teamF.getRoleSupFive() : roleSupFive)
                .mutate();
        em.persist(teamF);
        return teamF;
    }
}
