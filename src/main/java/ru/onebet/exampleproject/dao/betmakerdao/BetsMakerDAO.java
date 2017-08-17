package ru.onebet.exampleproject.dao.betmakerdao;

import ru.onebet.exampleproject.model.betsmaked.MakedBets;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.coupleteambets.EventBetweenCoupleTeam;
import ru.onebet.exampleproject.model.team.Team;

import java.math.BigDecimal;
import java.util.List;

public interface BetsMakerDAO <T extends Team, E extends EventBetweenCoupleTeam, M extends MakedBets> {

    M makeBet(ClientImpl client,
                 Admin root,
                 E event,
                 T bettingTeam,
                 BigDecimal amount);

    List<M> allMakedBets();

    List<M> allBetsOfOneClient(String login);
}
