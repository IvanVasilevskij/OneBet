package ru.onebet.exampleproject.dao.userdao;

import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO {

    ClientImpl createClient(String login, String password, String email);

    ClientImpl findClient(String login);

    Admin createAdmin(String login, String password, String email);

    Admin findAdmin(String login);

    void deleteUserByLogin(String login);

    Admin ensureRootUser();

    ClientImpl ensureClientForEmitMoneyOperation();

    ClientImpl checkPassword(String login, String password);

    void checkBalanceForBet(ClientImpl client, BigDecimal amount);

    List<ClientImpl> getAllClients();

    List<Admin> getAllAdmins();

}
