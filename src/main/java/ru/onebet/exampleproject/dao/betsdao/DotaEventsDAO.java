package ru.onebet.exampleproject.dao.betsdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.team.DotaTeam;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
                .teamFirst(teamFirst)
                .teamSecond(teamSecond)
                .date(timeOfTheGame)
                .percentForTeamFirst(percentForTeamFirst)
                .persentToDrow(percentForDraw)
                .persentForTeamSecond(percentForTeamSecond)
                .build();

        em.persist(event);
        em.getTransaction().commit();

        return event;
    }

    @Override
    public List<DotaEvent> allEvents() {
        try {
            List<DotaEvent> events = em.createQuery("from DotaEvent").getResultList();
            return events;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public List<DotaEvent> allEventsWithThisTeam(DotaTeam team) { //NEED ADD TEST
        try {
            List<DotaEvent> events = em.createQuery("from DotaEvent").getResultList();
            List<DotaEvent> resultListEventsWithThisTeam = events.stream()
                    .filter(c -> c.getTeamFirst().equals(team) || c.getTeamSecond().equals(team))
                    .collect(Collectors.toList());
            return resultListEventsWithThisTeam;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public void checkThatThisEventHaveTheTeam (DotaEvent event, DotaTeam team) {
        if (!event.getTeamFirst().equals(team) && !event.getTeamSecond().equals(team)) {
            throw new IllegalArgumentException("This comand doesen't paricipating at this event");
        }
    }

    //добавить выборку event'ов по дате без времени. только день недели!!!
}
