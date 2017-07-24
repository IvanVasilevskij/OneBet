package ru.onebet.exampleproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.DotaBets;
import ru.onebet.exampleproject.model.DotaTeam;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DotaBetsDAO {

    private EntityManager em;

    @Autowired
    public DotaBetsDAO(EntityManager em) {
        this.em = em;
    }

    public DotaBets createBet(
            DotaTeam comandOne,
            DotaTeam comandTwo,
            Date timeOfTheGame,
            double percentForComandOne,
            double percentForDraw,
            double percentForComandTwo) {

        em.getTransaction().begin();

        DotaBets bet = DotaBets.newBuilder()
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

    public DotaBets findBet(String searchingMark) {
        try {
            return em.createNamedQuery(DotaBets.FindBySearchingMark, DotaBets.class)
                    .setParameter("searchingMark", searchingMark)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public List<DotaBets> allBets() {
        try {
            List<DotaBets> bets = em.createQuery("from DotaBets").getResultList();
            return bets;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public String makeSearchingMarkOfDotaBet(DotaTeam comandOne,
                                             DotaTeam comandTwo,
                                             Date timeOfTheGame) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        return comandOne.getTeamName() + " " +
                comandTwo.getTeamName() + " " +
                dateFormat.format(timeOfTheGame) + " " +
                timeFormat.format(timeOfTheGame);
    }
}
