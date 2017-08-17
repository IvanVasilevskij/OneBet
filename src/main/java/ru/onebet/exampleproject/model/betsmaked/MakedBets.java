package ru.onebet.exampleproject.model.betsmaked;

import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.coupleteambets.EventBetweenCoupleTeam;
import ru.onebet.exampleproject.model.team.Team;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MakedBets <T extends Team, E extends EventBetweenCoupleTeam> {

    int getId();

    LocalDateTime getDate();

    T getBettingTeam();

    ClientImpl getClient();

    E getEvent();

    BigDecimal getAmount();
}
