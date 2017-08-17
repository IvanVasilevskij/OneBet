package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.onebet.exampleproject.dto.*;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
public class GeneralControllers {
    private final DotaEventsDAO daoDotaEvent;

    @Autowired
    public GeneralControllers(DotaEventsDAO daoDotaEvent) {
        this.daoDotaEvent = daoDotaEvent;
    }

    @GetMapping("/")
    public String returnToHomaPage() {
        return "/free/home-page";
    }

    @GetMapping("/OneBet.ru/free/403")
    public String returnAccessDeniedPage() {
        return "/free/403";
    }

    @GetMapping("/OneBet.ru/to-home-page")
    public String toHomePage() {
        return "/free/home-page";
    }

    @GetMapping("/OneBet.ru/free/to-all-event-page")
    public String toAllEventPage(ModelMap model) {
        EventDTO bean = new EventDTO();
        bean.setList(daoDotaEvent.allEvents());
        model.put("events", bean);
        return "free/all-event";
    }
}