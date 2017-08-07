package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.dto.DotaTeamDTO;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityManager;

@Controller
public class TeamManipulationControllers {
    private final EntityManager em;
    private final DotaTeamDAO daoDotaTeam;

    @Autowired
    public TeamManipulationControllers(EntityManager em,
                                       DotaTeamDAO daoDotaTeam) {
        this.em = em;
        this.daoDotaTeam = daoDotaTeam;
    }

    @PostMapping("/createTeam")
    public String createDotaTeam(@RequestParam String teamName,
                                 ModelMap model) {

        if (daoDotaTeam.findTeamByTeamName(teamName) != null) {
            return "/admin/incorrect-teamname";
        } else {
            em.getTransaction().begin();
            DotaTeam team = daoDotaTeam.createTeam(teamName);
            em.getTransaction().commit();

            DotaTeamDTO bean = new DotaTeamDTO();
            bean.setAllTeam(daoDotaTeam.getAllTeams());

            model.put("team", bean);

            return "admin/all-created-team";
        }
    }

    @PostMapping("/updateDotaTeamInformation")
    public String updateDotaTeamInformation(@RequestParam String teamName,
                                            @RequestParam String roleMid,
                                            @RequestParam String roleCarry,
                                            @RequestParam String roleHard,
                                            @RequestParam String roleSupFour,
                                            @RequestParam String roleSupFive,
                                            ModelMap model) {

        em.getTransaction().begin();
        DotaTeam teamF = daoDotaTeam.findTeamByTeamName(teamName);
        em.getTransaction().commit();

        em.getTransaction().begin();
        DotaTeam team = daoDotaTeam.updateDOtaTeam(teamF, roleMid, roleCarry, roleHard, roleSupFour, roleSupFive);
        em.getTransaction().commit();

        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setAllTeam(daoDotaTeam.getAllTeams());

        model.put("team", bean);
        return "admin/all-created-team";
    }

    @PostMapping("/goUpdateDotaTeam")
    public String goUpdateDotaTeam(@RequestParam String teamNameForUpdate,
                                   ModelMap model) {
        DotaTeam team = daoDotaTeam.findTeamByTeamName(teamNameForUpdate);
        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setTeam(team);

        model.put("team", bean);
        return "admin/update-team";
    }

    @PostMapping("/deleteDotaTeam")
    public String deleteDotaTeam(@RequestParam String teamForDelete,
                                 ModelMap model) {
        em.getTransaction().begin();
        daoDotaTeam.deleteTeamByTeamName(teamForDelete);
        em.getTransaction().commit();

        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setAllTeam(daoDotaTeam.getAllTeams());

        model.put("team", bean);
        return "admin/all-created-team";
    }

    @GetMapping("/toAllCreatedDotaTeamPage")
    public String toAllCreatedDotaTeamPage(ModelMap model) {

        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setAllTeam(daoDotaTeam.getAllTeams());

        model.put("team", bean);
        return "admin/all-created-team";
    }

    @GetMapping("/goCreateNewDotaTeam")
    public String goCreateNewDotaTeam() {
        return "admin/create-team";
    }
}
