package ru.onebet.exampleproject.dao.betsdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DotaEventsDAO implements EventsDAO <DotaTeam, DotaEvent> {

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

        em.getTransaction().begin();

        DotaEvent event = DotaEvent.Builder()
                .withTeamFirst(teamFirst)
                .withTeamSecond(teamSecond)
                .withDate(timeOfTheGame)
                .withPercentForTeamFirst(percentForTeamFirst)
                .withPersentToDrow(percentForDraw)
                .withPersentForTeamSecond(percentForTeamSecond)
                .build();

        em.persist(event);
        em.getTransaction().commit();

        return event;
    }

    @Override
    public List<DotaEvent> allEvents() {
        try {
            return em.createQuery("from DotaEvent", DotaEvent.class).getResultList();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public List<DotaEvent> allEventsWithThisTeam(DotaTeam team) {
        try {
            return em.createQuery("from DotaEvent", DotaEvent.class).getResultList().stream()
                    .filter(c -> (c.getTeamFirst().equals(team) || c.getTeamSecond().equals(team)))
                    .collect(Collectors.toList());
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public void checkThatThisEventHaveTheTeam (DotaEvent event, DotaTeam team) {
        if (!event.getTeamFirst().equals(team) && !event.getTeamSecond().equals(team)) {
            throw new IllegalArgumentException("This comand doesen't paricipating at this withEvent");
        }
    }

    @Override
    public List<DotaEvent> chooseAllEventInEnteredDate(LocalDateTime date) {
        try {
            LocalDateTime startOfDayAtEnteredDate = date.withHour(0).withMinute(0);
            LocalDateTime endOfDayAtEnteredDate = date.withHour(23).withMinute(59);
            return em.createQuery("from DotaEvent", DotaEvent.class).getResultList().stream()
                    .filter(c -> (c.getDate().compareTo(startOfDayAtEnteredDate) >= 0 && c.getDate().compareTo(endOfDayAtEnteredDate) <= 0))
                    .collect(Collectors.toList());
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }
}
