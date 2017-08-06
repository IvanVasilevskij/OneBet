package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.onebet.exampleproject.dao.betmakerdao.DotaBetsMakerDAO;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;

import javax.persistence.EntityManager;

@Controller
public class ControllersForAuthenticator {
    private final UserDAOImpl daoUser;
    private final DotaEventsDAO daoEvent;
    private final EntityManager em;
    private final DotaTeamDAO daoDotaTeam;
    private final DotaBetsMakerDAO daoBetsMaker;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ControllersForAuthenticator(EntityManager em,
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

    @GetMapping("/tologinpage")
    public String toLoginPage() {
        return "withoutrole/login";
    }

    @PostMapping("/signin")
    public String login() {
        return "free/home-page";
    }

}
