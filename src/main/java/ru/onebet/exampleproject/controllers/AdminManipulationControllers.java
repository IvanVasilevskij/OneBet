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
import ru.onebet.exampleproject.dto.AdminDTO;
import ru.onebet.exampleproject.dto.TransactionDTO;
import ru.onebet.exampleproject.dto.UserListDTO;
import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import java.math.BigDecimal;

@Controller
public class AdminManipulationControllers {
    private final UserDAOImpl daoUser;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TransactionDAO daoTransaction;

    @Autowired
    public AdminManipulationControllers(UserDAOImpl daoUser,
                                        BCryptPasswordEncoder passwordEncoder,
                                        TransactionDAO daoTransaction) {
        this.daoUser = daoUser;
        this.passwordEncoder = passwordEncoder;
        this.daoTransaction = daoTransaction;
    }

    @GetMapping("/OneBet.ru/admin/list-of-all-users")
    @Transactional
    public String users(ModelMap model) {
        UserListDTO bean = new UserListDTO();

        bean.setClients(daoUser.getAllClients());
        bean.setAdmins(daoUser.getAllAdmins());

        model.put("users", bean);

        return "admin/all-users";
    }

    @PostMapping("/OneBet.ru/admin/update-admin-details")
    @Transactional
    public String updateAdminDetails(@RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String email,
                                          ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Admin admin = daoUser.findAdmin(username);

        daoUser.updateInformationForAdmin(admin, firstName, lastName, email);

        AdminDTO bean = new AdminDTO();
        bean.setAdmin(admin);
        model.put("user", bean);

        AdminDTO beanSecond = new AdminDTO();
        beanSecond.setAdmin(daoUser.findAdmin("root"));
        model.put("root", beanSecond);

        TransactionDTO beanThree = new TransactionDTO();
        beanThree.setTransaction(daoTransaction.transactionListForAdmin(admin.getLogin()));
        model.put("transaction", beanThree);

        return "admin/private-room";
    }

    @GetMapping("/OneBet.ru/admin/update-admins-details")
    public String toUpdateAdminDetails() {
        return "admin/update-admin-details";
    }

    @GetMapping("/admin-root/to-create-new-admin-page")
    public String toCreateNewAdmin() {
        return "admin/create-new-admin";
    }

    @GetMapping("/OneBet.ru/admin/private-room")
    public String enterAdminPrivateRoom(ModelMap model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        Admin admin = daoUser.findAdmin(username);
        AdminDTO bean = new AdminDTO();
        bean.setAdmin(admin);
        model.put("user", bean);

        AdminDTO beanSecond = new AdminDTO();
        beanSecond.setAdmin(daoUser.findAdmin("root"));
        model.put("root", beanSecond);

//        TransactionUserDTO beanThree = new TransactionUserDTO();
//        beanThree.setTransaction(daoTransaction.transactionList().stream()
//                .filter(c -> c.getAdmin().equals(admin))
//                .collect(Collectors.toList()));

        TransactionDTO beanThree = new TransactionDTO();
        beanThree.setTransaction(daoTransaction.transactionListForAdmin(admin.getLogin()));
        model.put("ta", beanThree);

        return "admin/private-room";
    }

    @PostMapping("/OneBet.ru/admin-root/create-new-admin")
    @Transactional
    public String createNewAdmin(@RequestParam String login,
                                 @RequestParam String enteredPassword,
                                 @RequestParam String repeatedPassword,
                                 ModelMap model) {
        if (daoUser.findClient(login) != null) return "withoutrole/username-already-used";
        else if (daoUser.findAdmin(login) != null) return "withoutrole/username-already-used";
        else {
            if (!enteredPassword.equals(repeatedPassword)) return "withoutrole/incorrect-password";
            String hashedPassword = passwordEncoder.encode(enteredPassword);

            Admin admin = daoUser.createAdmin(login, hashedPassword);

            model.put("login", admin.getLogin());

            return "free/user-successfully-created";
        }
    }

    @GetMapping("/OneBet.ru/admin-root/to-emit-money-page")
    public String toEmitMoneyPage() {
        return "/admin/emit-money";
    }

    @PostMapping("/admin-root/emit-money")
    @Transactional
    public String emitMoney(@RequestParam String emit,
                            ModelMap model) {

        ClientImpl client = daoUser.findClient("client");
        Admin root = daoUser.findAdmin("root");

        daoTransaction.emitMoney(root, client, new BigDecimal(emit));

        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Admin admin = daoUser.findAdmin(username);

        AdminDTO bean = new AdminDTO();
        bean.setAdmin(admin);
        model.put("user", bean);

        AdminDTO beanSecond = new AdminDTO();
        beanSecond.setAdmin(daoUser.findAdmin("root"));
        model.put("root", beanSecond);

        TransactionDTO beanThree = new TransactionDTO();
        beanThree.setTransaction(daoTransaction.transactionListForAdmin(admin.getLogin()));
        model.put("ta", beanThree);

        return "admin/private-room";
    }

    @GetMapping("/OneBet.ru/admin/all-trasaction-list")
    public String allTrnsactionList(ModelMap model) {
        TransactionDTO bean = new TransactionDTO();
        bean.setTransaction(daoTransaction.transactionList());
        model.put("ta", bean);
        return "admin/all-transaction-list";
    }
}
