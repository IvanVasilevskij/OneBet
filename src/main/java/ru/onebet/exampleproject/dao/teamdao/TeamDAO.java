package ru.onebet.exampleproject.dao.teamdao;

import ru.onebet.exampleproject.model.team.Team;

import java.util.List;

public interface TeamDAO <T extends Team> {

    T createTeam(String teamName);

    T findTeamByTeamName(String teamName);

    void deleteTeamByTeamName(String teamName);

    List<T> getAllTeams();
}
