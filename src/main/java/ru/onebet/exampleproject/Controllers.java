package ru.onebet.exampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.onebet.exampleproject.dao.betsdao.DotaEventsDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.model.users.ClientImpl;

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

    @RequestMapping(method = RequestMethod.POST, value = "/create-user")
    public String createUser(@RequestParam String login, String enteredPassword, String repetedPassword, ModelMap model) {
        if (enteredPassword.compareTo(repetedPassword) != 0) {
            return "incorrect-password";
        }
        ClientImpl client = daoUser.createClient(login,enteredPassword);

        model.put("login", client.getLogin());

        return "user-successfully-created";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all-events")
    public String allEvents(ModelMap model) {
        AllEventsBean bean = new AllEventsBean();
        bean.setEvents(daoEvent.allEvents());

        model.put("events", bean);
        return "all-events";
    }
}
