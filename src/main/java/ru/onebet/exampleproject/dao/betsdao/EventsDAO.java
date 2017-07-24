package ru.onebet.exampleproject.dao.betsdao;

import ru.onebet.exampleproject.model.coupleteambets.EventBetweenCoupleTeam;

import java.util.Date;
import java.util.List;

public interface EventsDAO {

    public <B extends EventBetweenCoupleTeam> B createEvent(
            String teamOne,
            String teamTwo,
            Date timeOfTheGame,
            double percentForComandOne,
            double percentForDraw,
            double percentForComandTwo);

    public <B extends EventBetweenCoupleTeam> B findEvent(String searchingMark);

    public <B extends EventBetweenCoupleTeam> List<B> allEvents();

    public <B extends EventBetweenCoupleTeam> List<B> allEventsWithThisTeam(String teamName);

    public String createSearchingMark(String teamOneName,
                                      String teamTwoName,
                                      Date timeOfTheGame);
}
