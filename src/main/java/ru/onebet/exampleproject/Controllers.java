package ru.onebet.exampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.onebet.exampleproject.dao.betsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.ClientImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Controllers {
    private final UserDAOImpl daoUser;
    private final DotaEventsDAO daoEvent;

    @Autowired
    public Controllers(UserDAOImpl daoUser,
                       DotaEventsDAO daoEvent) {
        this.daoUser = daoUser;
        this.daoEvent = daoEvent;
    }

    @PostMapping("/create-user")
    public String createUser(@RequestParam String login,@RequestParam String enteredPassword,@RequestParam String repeatedPassword, ModelMap model) {
        if (!enteredPassword.equals(repeatedPassword)) {
            return "incorrect-password";
        }
        ClientImpl client = daoUser.createClient(login,enteredPassword);

        model.put("login", client.getLogin());

        return "user-successfully-created";
    }

    @GetMapping("/events")
    public String events(ModelMap model) {
        EventsBean bean = new EventsBean();
        List<String> a = new ArrayList<>();
        a.add("aaa0");
        a.add("bbb");
        bean.setEvents(a);

        model.put("events", bean);
        return "/events";
    }
}
