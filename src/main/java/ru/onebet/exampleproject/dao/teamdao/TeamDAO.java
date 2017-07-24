package ru.onebet.exampleproject.dao.teamdao;

import ru.onebet.exampleproject.model.team.Team;

public interface TeamDAO {

    public <T extends Team> T createTeam(String teamName, String roleMid, String roleCarry, String roleHard, String roleSupFour, String roleSupFive);

    public <T extends Team> T findTeamByTeamName(String teamName);

    public void deleteTeamByTeamName(String teamName);

    }
