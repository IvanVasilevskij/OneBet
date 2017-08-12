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

    @Transactional(readOnly = true)
    public DotaTeam findTeamByTeamName(String teamName) {
        try {
            return em.createNamedQuery(DotaTeam.FindByTeamName, DotaTeam.class)
                    .setParameter("teamName", teamName)
                    .getSingleResult();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public DotaTeam createTeam(String teamName) throws EntityExistsException {
                DotaTeam team = DotaTeam.Builder()
                        .withTeamName(teamName)
                        .build();
                em.persist(team);
                return team;
    }

    @Override
    public void deleteTeamByTeamName(String teamName) {
        DotaTeam team = findTeamByTeamName(teamName);
        if (team != null) {
            em.remove(team);
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
