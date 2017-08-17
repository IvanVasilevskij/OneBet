package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.betsmaked.MakedBetsOfDota;

import java.util.List;

public class BetDTO {
    private List<MakedBetsOfDota> bets;

    public List<MakedBetsOfDota> getBets() {
        return bets;
    }

    public void setBets(List<MakedBetsOfDota> bets) {
        this.bets = bets;
    }
}
