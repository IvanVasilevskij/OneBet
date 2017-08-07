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
import ru.onebet.exampleproject.dto.AdminDTO;
import ru.onebet.exampleproject.dto.UserListDTO;
import ru.onebet.exampleproject.model.users.Admin;

import javax.persistence.EntityManager;

@Controller
public class AdminManipulationControllers {
    private final UserDAOImpl daoUser;
    private final EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminManipulationControllers(EntityManager em,
                                         UserDAOImpl daoUser,
                                         BCryptPasswordEncoder passwordEncoder) {
        this.em = em;
        this.daoUser = daoUser;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String users(ModelMap model) {
        UserListDTO bean = new UserListDTO();

        bean.setClients(daoUser.getAllClients());
        bean.setAdmins(daoUser.getAllAdmins());

        model.put("users", bean);

        return "admin/all-users";
    }

    @PostMapping("/updateAdminDetails")
    public String updateAdminDetails(@RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String email,
                                          ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Admin admin = daoUser.findAdmin(username);

        em.getTransaction().begin();
        daoUser.updateInformationForAdmin(admin, firstName, lastName, email);
        em.getTransaction().commit();

        AdminDTO bean = new AdminDTO();
        bean.setAdmin(admin);
        model.put("user", bean);

        return "admin/private-room";
    }

    @GetMapping("/toUpdateAdminDetails")
    public String toUpdateAdminDetails() {
        return "admin/update-admin-details";
    }

    @GetMapping("/toCreateNewAdmin")
    public String toCreateNewAdmin() {
        return "admin/create-new-admin";
    }

    @GetMapping("/enterAdminPrivateRoom")
    public String enterAdminPrivateRoom(ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Admin admin = daoUser.findAdmin(username);
        AdminDTO bean = new AdminDTO();
        bean.setAdmin(admin);
        model.put("user", bean);
        return "admin/private-room";
    }

    @PostMapping("/createNewAdmin")
    public String createNewAdmin(@RequestParam String login,
                                 @RequestParam String enteredPassword,
                                 @RequestParam String repeatedPassword,
                                 ModelMap model) {
        if (daoUser.findClient(login) != null) return "withoutrole/username-already-used";
        else if (daoUser.findAdmin(login) != null) return "withoutrole/username-already-used";
        else {
            if (!enteredPassword.equals(repeatedPassword)) return "withoutrole/incorrect-password";
            String hashedPassword = passwordEncoder.encode(enteredPassword);

            em.getTransaction().begin();
            Admin admin = daoUser.createAdmin(login, hashedPassword);
            em.getTransaction().commit();

            model.put("login", admin.getLogin());

            return "free/user-successfully-created";
        }
    }
}
