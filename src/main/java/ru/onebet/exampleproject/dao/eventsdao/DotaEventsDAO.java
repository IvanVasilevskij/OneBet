package ru.onebet.exampleproject.dao.eventsdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class DotaEventsDAO implements EventsDAO <DotaTeam, DotaEvent> {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public DotaEventsDAO(EntityManager em) {
        this.em = em;
    }


    @Override
    public DotaEvent createEvent(
            DotaTeam teamFirst,
            DotaTeam teamSecond,
            LocalDateTime timeOfTheGame,
            double percentForTeamFirst,
            double percentForDraw,
            double percentForTeamSecond) {

        DotaEvent event = DotaEvent.Builder()
                .withTeamFirst(teamFirst)
                .withTeamSecond(teamSecond)
                .withDate(timeOfTheGame)
                .withPercentForTeamFirst(percentForTeamFirst)
                .withPersentToDrow(percentForDraw)
                .withPersentForTeamSecond(percentForTeamSecond)
                .build();

        em.persist(event);
        return event;
    }

    @Override
    public List<DotaEvent> allEvents() {
            return em.createQuery("from DotaEvent", DotaEvent.class).getResultList();
    }

//    @Override
//    public List<DotaEvent> allEventsWithThisTeam(DotaTeam team) {
//            return em.createQuery("from DotaEvent", DotaEvent.class).getResultList().stream()
//                    .filter(c -> (c.getTeamFirst().equals(team) || c.getTeamSecond().equals(team)))
//                    .collect(Collectors.toList());
//    }

    @Override
    public List<DotaEvent> allEventsWithThisTeam(String teamName) {
        try {
            return em.createNamedQuery(DotaEvent.FindEventsByTeamName, DotaEvent.class)
                    .setParameter("teamName", teamName)
                    .getResultList();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public boolean checkThatThisEventHaveTheTeam (DotaEvent event, DotaTeam team) {
        return event.getTeamFirst().equals(team) || event.getTeamSecond().equals(team);
    }

//    @Override
//    public List<DotaEvent> chooseAllEventInEnteredDate(LocalDateTime date) {
//            LocalDateTime startOfDayAtEnteredDate = date.withHour(0).withMinute(0);
//            LocalDateTime endOfDayAtEnteredDate = date.withHour(23).withMinute(59);
//            return em.createQuery("from DotaEvent", DotaEvent.class).getResultList().stream()
//                    .filter(c -> (c.getDate().compareTo(startOfDayAtEnteredDate) >= 0 && c.getDate().compareTo(endOfDayAtEnteredDate) <= 0))
//                    .collect(Collectors.toList());
//    }

    @Override
    public List<DotaEvent> chooseAllEventInEnteredDate(LocalDateTime date) {
        LocalDateTime startOfDayAtEnteredDate = date.withHour(0).withMinute(0);
        LocalDateTime endOfDayAtEnteredDate = date.withDayOfMonth(date.getDayOfMonth()+1);

        return em.createNamedQuery(DotaEvent.FindEventsByEnteredDate, DotaEvent.class)
                .setParameter("dateS", startOfDayAtEnteredDate)
                .setParameter("dateE", endOfDayAtEnteredDate)
                .getResultList();
    }
}
