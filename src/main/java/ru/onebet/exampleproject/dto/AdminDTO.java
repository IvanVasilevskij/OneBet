package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.users.Admin;

public class AdminDTO {
    private Admin admin;

    public AdminDTO(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}
