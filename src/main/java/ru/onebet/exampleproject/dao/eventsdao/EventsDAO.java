package ru.onebet.exampleproject.dao.eventsdao;

import ru.onebet.exampleproject.model.coupleteambets.EventBetweenCoupleTeam;
import ru.onebet.exampleproject.model.team.Team;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsDAO<T extends Team,E extends EventBetweenCoupleTeam> {

    E createEvent(
            T teamFirst,
            T teamSecond,
            LocalDateTime timeOfTheGame,
            double percentForTeamFirst,
            double percentForDraw,
            double percentForTeamSecond);

    List<E> allEvents();

    List<E> allEventsWithThisTeam(String teamName);

    boolean checkThatThisEventHaveTheTeam (E event, T team);

    List<E> chooseAllEventInEnteredDate(LocalDateTime date);
}
