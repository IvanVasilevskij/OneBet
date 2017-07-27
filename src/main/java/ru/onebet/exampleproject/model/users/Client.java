package ru.onebet.exampleproject.model.users;

import ru.onebet.exampleproject.model.betsmaked.MakedBets;

import java.util.List;

public interface Client <B extends MakedBets> extends User {

    List<B> getBets();
}
