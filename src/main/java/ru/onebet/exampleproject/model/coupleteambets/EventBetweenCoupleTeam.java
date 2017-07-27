package ru.onebet.exampleproject.model.coupleteambets;

import ru.onebet.exampleproject.model.team.Team;

import java.time.LocalDateTime;

public interface EventBetweenCoupleTeam<T extends Team> {

    int getId();

    LocalDateTime getDate();

    T getTeamFirst();

    T getTeamSecond();

    double getPercentForTeamFirst();

    double getPersentForDrow();

    double getPercentForTeamSecond();
}
