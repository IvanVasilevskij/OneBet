package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.Bets;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BetsDAO {

    private EntityManager em;
    private ComandDAO daoC;

    @Autowired
    public  BetsDAO(EntityManager em,
                    ComandDAO daoC) {
        this.em = em;
        this.daoC = daoC;
    }

    public Bets createBet(
            String comandOne,
            String comandTwo,
            String timeOfTheGame,
            double percentForComandOne,
            double percentForDraw,
            double percentForComandTwo) {

        em.getTransaction().begin();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        Date date = null;
        try {
            date = sdf.parse(timeOfTheGame);
        } catch (ParseException p) {
            throw new IllegalArgumentException("Failed date format. Need yyyy.MM.dd hh:mm");
        }

        Bets bet = new Bets();
        bet.setComandOne(daoC.findComandByComandName(comandOne));
        bet.setComandTwo(daoC.findComandByComandName(comandTwo));
        bet.setDate(date);
        bet.setPersentComandOne(percentForComandOne);
        bet.setPersentComandTwo(percentForComandTwo);
        bet.setPersentToDrow(percentForDraw);
        bet.setSearchingMark(comandOne+comandTwo+timeOfTheGame);
        em.persist(bet);
        em.getTransaction().commit();

        return bet;
    }


    public Bets findBet(String searchingMark) {
        try {
            return em.createNamedQuery(Bets.FindBySearchingMark, Bets.class)
                    .setParameter("searchingMark", searchingMark)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public List<Bets> allBets() {
        try {
            List<Bets> bets = em.createQuery("from Bets").getResultList();
            return bets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
