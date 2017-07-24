package ru.onebet.exampleproject.dao.betsdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DotaEventsDAO implements EventsDAO {

    private EntityManager em;
    private DotaTeamDAO daoC;


    @Autowired
    public DotaEventsDAO(EntityManager em,
                         DotaTeamDAO daoC) {
        this.em = em;
        this.daoC = daoC;
    }



    public DotaEvent createEvent(
            String nameOfTeamOne,
            String nameOfTeamTwo,
            Date timeOfTheGame,
            double percentForTeamOne,
            double percentForDraw,
            double percentForTeamTwo) {

        em.getTransaction().begin();

        DotaTeam teamOne = daoC.findTeamByTeamName(nameOfTeamOne);
        DotaTeam teamTwo = daoC.findTeamByTeamName(nameOfTeamTwo);
        String searchingMark = createSearchingMark(teamOne.getTeamName(),
                teamTwo.getTeamName(),
                timeOfTheGame);

        DotaEvent bet = DotaEvent.newBuilder()
                .comandOne(teamOne)
                .comandTwo(teamTwo)
                .date(timeOfTheGame)
                .persentComandOne(percentForTeamOne)
                .persentComandTwo(percentForTeamTwo)
                .persentToDrow(percentForDraw)
                .searchingMark(searchingMark)
                .build();

        em.persist(bet);
        em.getTransaction().commit();

        return bet;
    }

    public DotaEvent findEvent(String searchingMark) {
        try {
            return em.createNamedQuery(DotaEvent.FindBySearchingMark, DotaEvent.class)
                    .setParameter("searchingMark", searchingMark)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public List<DotaEvent> allEvents() {
        try {
            List<DotaEvent> events = em.createQuery("from DotaEvent").getResultList();
            return events;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public List<DotaEvent> allEventsWithThisTeam(String teamName) { //NEED ADD TEST
        try {
            List<DotaEvent> resultListEventsWithThisTeam = new ArrayList<>();
            DotaTeam team = daoC.findTeamByTeamName(teamName);
            List<DotaEvent> events = em.createQuery("from DotaEvent").getResultList();
            for (DotaEvent betsWithThisComand : events) {
                if (betsWithThisComand.getTeamOne() == team || betsWithThisComand.getTeamTwo() == team) {
                    resultListEventsWithThisTeam.add(betsWithThisComand);
                }
            }
            return resultListEventsWithThisTeam;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    public String createSearchingMark(String teamOneName,
                                      String teamTwoName,
                                      Date timeOfTheGame) { //NEED ADD TEST

        DotaTeam teamOne = daoC.findTeamByTeamName(teamOneName);
        DotaTeam teamTwo = daoC.findTeamByTeamName(teamTwoName);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        return teamOne.getTeamName() + " " +
                teamTwo.getTeamName() + " " +
                dateFormat.format(timeOfTheGame) + " " +
                timeFormat.format(timeOfTheGame);
    }
}
