package ru.onebet.exampleproject.model.coupleteambets;

import ru.onebet.exampleproject.model.team.Team;

import java.util.Date;

public interface EventBetweenCoupleTeam {

    public int getId();

    public Date getDate();

    public <T extends Team> T getTeamOne();

    public <T extends Team> T getTeamTwo();

    public double getPercentForTeamOne();

    public double getPersentForDrow();

    public double getPercentForTeamTwo();

    public String getSearchingMark();

    }
