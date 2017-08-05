package ru.onebet.exampleproject.dto;

import ru.onebet.exampleproject.model.users.ClientImpl;

public class ClientDTO {
    private ClientImpl client;

    public ClientDTO(ClientImpl client) {
        this.client = client;
    }

    public ClientImpl getClient() {
        return client;
    }
}
