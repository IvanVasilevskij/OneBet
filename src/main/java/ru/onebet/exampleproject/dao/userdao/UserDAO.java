package ru.onebet.exampleproject.dao.userdao;

import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;
import ru.onebet.exampleproject.model.users.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO {

    ClientImpl createClient(String login, String password);

    ClientImpl findClient(String login);

    Admin createAdmin(String login, String password);

    Admin findAdmin(String login);

    void updateInformationForClient(ClientImpl client, String firstName, String lastName, String email);

    void updateInformationForAdmin(Admin admin, String firstName, String lastName, String email);

    void deleteUserByLogin(String login);

    Admin ensureRootUser();

    ClientImpl ensureClientForEmitMoneyOperation();

    User checkPassword(User user, String password);

    boolean checkBalanceForBet(ClientImpl client, BigDecimal amount);

    List<ClientImpl> getAllClients();

    List<Admin> getAllAdmins();

    ClientImpl upBalance(ClientImpl client, BigDecimal amount);

}
