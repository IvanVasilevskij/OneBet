package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.BetsDota;
import ru.onebet.exampleproject.model.ComandOfDota;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BetsDotaDAO {

    private EntityManager em;



    @Autowired
    public BetsDotaDAO(EntityManager em) {
        this.em = em;
    }

    public BetsDota createBet(
            ComandOfDota comandOne,
            ComandOfDota comandTwo,
            Date timeOfTheGame,
            double percentForComandOne,
            double percentForDraw,
            double percentForComandTwo) {

        em.getTransaction().begin();

        BetsDota bet = BetsDota.newBuilder()
                .comandOne(comandOne)
                .comandTwo(comandTwo)
                .date(timeOfTheGame)
                .persentComandOne(percentForComandOne)
                .persentComandTwo(percentForComandTwo)
                .persentToDrow(percentForDraw)
                .searchingMark(makeSearchingMarkOfDotaBet(comandOne, comandTwo, timeOfTheGame))
                .build();

        em.persist(bet);
        em.getTransaction().commit();

        return bet;
    }

    public BetsDota findBet(String searchingMark) {
        try {
            return em.createNamedQuery(BetsDota.FindBySearchingMark, BetsDota.class)
                    .setParameter("searchingMark", searchingMark)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public List<BetsDota> allBets() {
        try {
            List<BetsDota> bets = em.createQuery("from BetsDota").getResultList();
            return bets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public String makeSearchingMarkOfDotaBet(ComandOfDota comandOne,
                                             ComandOfDota comandTwo,
                                             Date timeOfTheGame) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        return comandOne.getComandName() + " " +
                comandTwo.getComandName() + " " +
                dateFormat.format(timeOfTheGame) + " " +
                timeFormat.format(timeOfTheGame);
    }
}
