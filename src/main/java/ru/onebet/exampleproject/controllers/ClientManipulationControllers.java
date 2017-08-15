package ru.onebet.exampleproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.onebet.exampleproject.dao.TransactionDAO;
import ru.onebet.exampleproject.dao.userdao.UserDAOImpl;
import ru.onebet.exampleproject.dto.ClientDTO;
import ru.onebet.exampleproject.dto.TransactionDTO;
import ru.onebet.exampleproject.model.users.ClientImpl;

@Controller
public class ClientManipulationControllers {
    private final UserDAOImpl daoUser;
    private final TransactionDAO daoTransaction;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientManipulationControllers(UserDAOImpl daoUser,
                                         TransactionDAO daoTransaction,
                                         BCryptPasswordEncoder passwordEncoder) {
        this.daoUser = daoUser;
        this.daoTransaction = daoTransaction;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/OneBet.ru/anonymous/to-create-client-page")
    public String toCreateClientPage() {
        return "withoutrole/create-client";
    }

    @PostMapping("/OneBet.ru/anonymous/create-new-client")
    @Transactional
    public String createClient(@RequestParam String login,
                               @RequestParam String enteredPassword,
                               @RequestParam String repeatedPassword,
                               ModelMap model) {
        if (daoUser.findClient(login) != null) return "withoutrole/username-already-used";
        else if (daoUser.findAdmin(login) != null) return "withoutrole/username-already-used";
        else {
            if (!enteredPassword.equals(repeatedPassword)) return "withoutrole/incorrect-password";
            String hashedPassword = passwordEncoder.encode(enteredPassword);

            ClientImpl client = daoUser.createClient(login, hashedPassword);

            model.put("login", client.getLogin());

            return "free/user-successfully-created";
        }
    }

    @PostMapping("/OneBet.ru/client/update-client-details")
    @Transactional
    public String updateClientDetails(@RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam String email,
                                      ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        ClientImpl client = daoUser.findClient(username);

        daoUser.updateInformationForClient(client, firstName, lastName, email);

        ClientDTO bean = new ClientDTO();
        bean.setClient(client);
        model.put("user", bean);

        TransactionDTO beanThree = new TransactionDTO();
        beanThree.setTransaction(daoTransaction.transactionListForClient(client.getLogin()));
        model.put("ta", beanThree);

        return "client/private-room";
    }

    @GetMapping("/OneBet.ru/client/to-update-client-details-page")
    public String toUpdateClientDetails() {
        return "client/update-client-details";
    }

    @GetMapping("/OneBet.ru/client/private-room")
    public String enterClientPrivateRoom(ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        ClientImpl client = daoUser.findClient(username);

        ClientDTO bean = new ClientDTO();
        bean.setClient(client);
        model.put("user", bean);

        TransactionDTO beanThree = new TransactionDTO();
        beanThree.setTransaction(daoTransaction.transactionListForClient(client.getLogin()));
        model.put("ta", beanThree);

        return "client/private-room";
    }
}
