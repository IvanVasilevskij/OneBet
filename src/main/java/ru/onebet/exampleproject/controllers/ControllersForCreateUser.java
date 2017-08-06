package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.onebet.exampleproject.dao.betmakerdao.DotaBetsMakerDAO;
import ru.onebet.exampleproject.dao.eventsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.teamdao.DotaTeamDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;

@Controller
public class ControllersForCreateUser {
    private final UserDAOImpl daoUser;
    private final EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ControllersForCreateUser(EntityManager em,
                       UserDAOImpl daoUser,
                       BCryptPasswordEncoder passwordEncoder) {
        this.em = em;
        this.daoUser = daoUser;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/tocreatepage")
    public String toCreatePage() {
        return "withoutrole/create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestParam String login,
                             @RequestParam String enteredPassword,
                             @RequestParam String repeatedPassword,
                             ModelMap model) {
        if (daoUser.findClient(login) != null) return "withoutrole/username-already-used";
        else if (daoUser.findAdmin(login) != null) return "withoutrole/username-already-used";
        else {
            if (!enteredPassword.equals(repeatedPassword)) return "withoutrole/incorrect-password";
            String hashedPassword = passwordEncoder.encode(enteredPassword);

            em.getTransaction().begin();
            ClientImpl client = daoUser.createClient(login, hashedPassword);
            em.getTransaction().commit();

            model.put("login", client.getLogin());

            return "free/user-successfully-created";
        }
    }

    @GetMapping("/tohomepage")
    public String toHomePage() {
        return "free/home-page";
    }
}
