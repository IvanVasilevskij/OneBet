package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private final DotaTeamDAO daoDotaTeam;

    @Autowired
    public TeamManipulationControllers(DotaTeamDAO daoDotaTeam) {
        this.daoDotaTeam = daoDotaTeam;
    }

    @PostMapping("/OneBet.ru/admin/create-dota-team")
    @Transactional
    public String createDotaTeam(@RequestParam String teamName,
                                 ModelMap model) {

        if (daoDotaTeam.findTeamByTeamName(teamName) != null) {
            return "/admin/incorrect-teamname";
        } else {
            DotaTeam team = daoDotaTeam.createTeam(teamName);

            DotaTeamDTO bean = new DotaTeamDTO();
            bean.setAllTeam(daoDotaTeam.getAllTeams());

            model.put("team", bean);

            return "admin/all-created-team";
        }
    }

    @PostMapping("/OneBet.ru/updateDotaTeamInformation")
    public String updateDotaTeamInformation(@RequestParam String teamName,
                                            @RequestParam String roleMid,
                                            @RequestParam String roleCarry,
                                            @RequestParam String roleHard,
                                            @RequestParam String roleSupFour,
                                            @RequestParam String roleSupFive,
                                            ModelMap model) {


        DotaTeam teamF = daoDotaTeam.findTeamByTeamName(teamName);
        daoDotaTeam.updateDOtaTeam(teamF, roleMid, roleCarry, roleHard, roleSupFour, roleSupFive);

        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setAllTeam(daoDotaTeam.getAllTeams());

        model.put("team", bean);
        return "admin/all-created-team";
    }

    @PostMapping("/OneBet.ru/admin/to-update-dota-team-page")
    public String goUpdateDotaTeam(@RequestParam String teamNameForUpdate,
                                   ModelMap model) {
        DotaTeam team = daoDotaTeam.findTeamByTeamName(teamNameForUpdate);
        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setTeam(team);

        model.put("team", bean);
        return "admin/update-team";
    }

    @PostMapping("/OneBet.ru/admin/delete-dota-team")
    public String deleteDotaTeam(@RequestParam String teamForDelete,
                                 ModelMap model) {
        daoDotaTeam.deleteTeamByTeamName(teamForDelete);

        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setAllTeam(daoDotaTeam.getAllTeams());

        model.put("team", bean);
        return "admin/all-created-team";
    }

    @GetMapping("/OneBet.ru/admin/list-of-all-dota-team")
    public String toAllCreatedDotaTeamPage(ModelMap model) {

        DotaTeamDTO bean = new DotaTeamDTO();
        bean.setAllTeam(daoDotaTeam.getAllTeams());

        model.put("team", bean);
        return "admin/all-created-team";
    }

    @GetMapping("/OneBet.ru/admin/to-create-dota-team-page")
    public String goCreateNewDotaTeam() {
        return "admin/create-team";
    }
}
