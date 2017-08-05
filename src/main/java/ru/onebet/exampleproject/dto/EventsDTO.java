package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;

import java.util.List;

public class EventsDTO {
    private List<DotaEvent> events;

    public List<DotaEvent> getEvents() {
        return events;
    }

    public void setEvents(List<DotaEvent> events) {
        this.events = events;
    }

}
