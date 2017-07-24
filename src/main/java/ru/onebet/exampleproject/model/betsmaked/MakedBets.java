package ru.onebet.exampleproject.model.betsmaked;

import ru.onebet.exampleproject.model.User;
import ru.onebet.exampleproject.model.coupleteambets.EventBetweenCoupleTeam;
import ru.onebet.exampleproject.model.team.Team;

import java.util.Date;

public interface MakedBets {

    public int getId();

    public Date getDate();

    public <T extends Team> T getTakeOfTeam();

    public User getUser();

    public <B extends EventBetweenCoupleTeam> B getBet();
}
