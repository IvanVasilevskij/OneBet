package ru.onebet.exampleproject.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.Model.Bets;
import ru.onebet.exampleproject.Model.ComandOfDota;

import javax.persistence.EntityManager;

@Service
public class BetsDAO {

    private EntityManager em;

    @Autowired
    public  BetsDAO(EntityManager em) {this.em = em;}

//    public Bets createBet(
//            ComandOfDota a,
//            ComandOfDota b,
//            double percentForComandA,
//            double percentForDraw,
//            double percentForComandB) {
//
//    }
//
//    public Bets makeBet(Double amount, ComandOfDota comandName) {
//
//    }
}
