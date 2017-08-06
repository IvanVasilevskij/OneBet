package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.onebet.exampleproject.dto.EventsDTO;
import ru.onebet.exampleproject.dto.UserListDTO;
import ru.onebet.exampleproject.dao.betmakerdao.DotaBetsMakerDAO;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.coupleteambets.DotaEvent;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
public class Controllers {
    private final UserDAOImpl daoUser;
    private final DotaEventsDAO daoEvent;
    private final EntityManager em;
    private final DotaTeamDAO daoDotaTeam;
    private final DotaBetsMakerDAO daoBetsMaker;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public Controllers(EntityManager em,
                       UserDAOImpl daoUser,
                       DotaTeamDAO daoDotaTeam,
                       DotaEventsDAO daoEvent,
                       DotaBetsMakerDAO daoBetsMaker,
                       BCryptPasswordEncoder passwordEncoder) {
        this.em = em;
        this.daoUser = daoUser;
        this.daoEvent = daoEvent;
        this.daoDotaTeam = daoDotaTeam;
        this.daoBetsMaker = daoBetsMaker;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String returnToHomaPage() {
        return "free/home-page";
    }

    @GetMapping("/events")
    public String events(ModelMap model) {
        EventsDTO bean = new EventsDTO();
        List<DotaEvent> list = daoEvent.allEvents();
        bean.setEvents(list);

        model.put("events", bean);
        return "user/events";
    }

    @GetMapping("/users")
    @Secured("ADMIN")
    public String users(ModelMap model) {
        UserListDTO bean = new UserListDTO();

        bean.setClients(daoUser.getAllClients());
        bean.setAdmins(daoUser.getAllAdmins());

        model.put("users", bean);

        return "admin/users";
    }

    @PostMapping("/update-user-informations")
    public String updateUserInformations(@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String email) {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof ClientImpl) {
            ClientImpl client = (ClientImpl) o;

            em.getTransaction().begin();
            daoUser.updateInformationForClient(client, firstName, lastName, email);
            em.getTransaction().commit();

            return "/user/private-room";
        } else {
            Admin admin = (Admin) o;

            em.getTransaction().begin();
            daoUser.updateInformationForAdmin(admin, firstName, lastName, email);
            em.getTransaction().commit();
            return "/user/private-room";
        }
    }
}