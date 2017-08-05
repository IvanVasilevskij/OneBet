package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.users.Admin;
import ru.onebet.exampleproject.model.users.ClientImpl;

import java.util.List;

public class UserListDTO {

    private List<ClientImpl> clients;
    private List<Admin> admins;


    public List<ClientImpl> getClients() {
        return clients;
    }

    public void setClients(List<ClientImpl> clients) {
        this.clients = clients;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}
