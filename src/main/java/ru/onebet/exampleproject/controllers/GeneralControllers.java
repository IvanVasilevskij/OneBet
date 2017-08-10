package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
    private final UserDAOImpl daoUser;
    private final DotaEventsDAO daoEvent;
    private final EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public GeneralControllers(EntityManager em,
                              UserDAOImpl daoUser,
                              DotaEventsDAO daoEvent,
                              BCryptPasswordEncoder passwordEncoder) {
        this.em = em;
        this.daoUser = daoUser;
        this.daoEvent = daoEvent;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String returnToHomaPage() {
        return "free/home-page";
    }

    @GetMapping("/free/403")
    public String returnAccessDeniedPage() {
        return "/free/403";
    }

    @GetMapping("/free-information/list-of-all-events")
    public String events(ModelMap model) {
        EventsDTO bean = new EventsDTO();
        List<DotaEvent> list = daoEvent.allEvents();
        bean.setEvents(list);

        model.put("events", bean);
        return "user/all-events";
    }

    @GetMapping("/to-home-page")
    public String toHomePage() {
        return "/free/home-page";
    }
}