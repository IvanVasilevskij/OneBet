package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;

import java.util.List;

public class EventDTO {
    private List<DotaEvent> list;

    public List<DotaEvent> getList() {
        return list;
    }

    public void setList(List<DotaEvent> list) {
        this.list = list;
    }
}
