package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.users.ClientImpl;

public class ClientDTO {
    private ClientImpl client;

    public ClientImpl getClient() {
        return client;
    }

    public void setClient(ClientImpl client) {
        this.client = client;
    }
}
