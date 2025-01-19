package ru.shiraku.testclient.service;

import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import ru.shiraku.testclient.dto.ClientResponse;
import ru.shiraku.testclient.dto.CreateClient;
import ru.shiraku.testclient.dto.UpdateRequest;
import ru.shiraku.testclient.entity.Client;
import ru.shiraku.testclient.entity.Contact;
import ru.shiraku.testclient.exceptions.NotFound;
import ru.shiraku.testclient.repository.ClientRepository;
import ru.shiraku.testclient.utlis.ClientMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClientService {
    private static final Log log = LogFactory.getLog(ClientService.class);
    private final ClientRepository clientRepository;
    private final ContactService contactService;

    public ClientService(ClientRepository clientRepository, ContactService contactService) {
        this.clientRepository = clientRepository;
        this.contactService = contactService;
    }

    @Transactional
    public void create(CreateClient request, Long contact_id) {
        Client client = new Client();
        client.setName(request.getName());
        client.setLast_name(request.getLast_name());
        client.setContact_id(contact_id);
        clientRepository.save(client);
    }


    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new NotFound("Client not found");
        }
        return optionalClient.get();
    }

    public List<ClientResponse> getClients() {
        List<Client> clients = clientRepository.findAll();
        List<Long> contactIds = clients.stream()
                .map(Client::getContact_id)
                .toList();
        Map<Long, Contact> contacts = contactService.getAllContactsByIds(contactIds);
        return clients.stream()
                .map(client -> ClientMapper.toDto(client, contacts.get(client.getContact_id())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long clientId, UpdateRequest updates) {
        Client client = getClientById(clientId);
        Contact contact = contactService.getContactById(client.getContact_id());
        if(updates.getName() != null) {
            client.setName(updates.getName());
        }
        if(updates.getLastName() != null) {
            client.setLast_name(updates.getLastName());
        }
        if(updates.getPhone() != null) {
            contact.setPhone(updates.getPhone());
        }
        if(updates.getEmail() != null) {
            contact.setEmail(updates.getEmail());
        }
        contactService.save(contact);
        clientRepository.save(client);
    }

    @Transactional
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

}
