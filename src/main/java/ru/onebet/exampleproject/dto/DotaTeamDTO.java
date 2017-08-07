package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.team.DotaTeam;

import java.util.List;

public class DotaTeamDTO {
    private DotaTeam team;
    private List<DotaTeam> allTeam;

    public DotaTeam getTeam() {
        return team;
    }

    public void setTeam(DotaTeam team) {
        this.team = team;
    }

    public List<DotaTeam> getAllTeam() {
        return allTeam;
    }

    public void setAllTeam(List<DotaTeam> allTeam) {
        this.allTeam = allTeam;
    }
}
