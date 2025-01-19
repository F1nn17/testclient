package ru.shiraku.testclient.utlis;

import ru.shiraku.testclient.dto.ClientResponse;
import ru.shiraku.testclient.entity.Client;
import ru.shiraku.testclient.entity.Contact;

public class ClientMapper {
    public static ClientResponse toDto(Client client, Contact contact) {
        return new ClientResponse(client.getClient_id(),
                client.getName(),
                client.getLast_name(),
                contact.getPhone(),
                contact.getEmail());
    }
}
