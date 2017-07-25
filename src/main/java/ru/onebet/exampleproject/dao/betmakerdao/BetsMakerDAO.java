package ru.onebet.exampleproject.dao.betmakerdao;

import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.coupleteambets.EventBetweenCoupleTeam;
import ru.onebet.exampleproject.model.team.Team;

import java.math.BigDecimal;
import java.util.List;

public interface BetsMakerDAO <T extends Team, E extends EventBetweenCoupleTeam> {

    void makeBet(ClientImpl client,
                 E event,
                 T bettingTeam,
                 BigDecimal amount);

    List<E> allMakedBets();
}
