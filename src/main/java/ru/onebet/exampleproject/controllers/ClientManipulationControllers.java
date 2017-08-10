package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.dto.ClientDTO;
import ru.onebet.exampleproject.model.users.ClientImpl;

import javax.persistence.EntityManager;

@Controller
public class ClientManipulationControllers {
    private final UserDAOImpl daoUser;
    private final EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientManipulationControllers(EntityManager em,
                                         UserDAOImpl daoUser,
                                         BCryptPasswordEncoder passwordEncoder) {
        this.em = em;
        this.daoUser = daoUser;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/anonymous/to-create-client-page")
    public String toCreateClientPage() {
        return "withoutrole/create-client";
    }

    @PostMapping("/anonymous/create-new-client")
    public String createClient(@RequestParam String login,
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

    @PostMapping("/client/update-client-details")
    public String updateClientDetails(@RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam String email,
                                      ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        ClientImpl client = daoUser.findClient(username);

        em.getTransaction().begin();
        daoUser.updateInformationForClient(client, firstName, lastName, email);
        em.getTransaction().commit();

        ClientDTO bean = new ClientDTO();
        bean.setClient(client);
        model.put("user", bean);

        return "client/private-room";
    }

    @GetMapping("/client/to-update-client-details-page")
    public String toUpdateClientDetails() {
        return "client/update-client-details";
    }

    @GetMapping("/client/private-room")
    public String enterClientPrivateRoom(ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        ClientImpl client = daoUser.findClient(username);

        ClientDTO bean = new ClientDTO();
        bean.setClient(client);
        model.put("user", bean);
        return "client/private-room";
    }
}
