package ru.shiraku.testclient.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.shiraku.testclient.entity.Contact;
import ru.shiraku.testclient.exceptions.NotFound;
import ru.shiraku.testclient.repository.ContactRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public Long createContact(String phone, String email) {
        Contact contact = new Contact();
        Random random = new Random();
        contact.setId(random.nextLong());
        contact.setPhone(phone);
        contact.setEmail(email);
        contactRepository.save(contact);
        return contact.getId();
    }

    public Contact getContactById(Long id) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isEmpty()) {
            throw new NotFound("Contact not found");
        }
        return contactOptional.get();
    }

    public Map<Long, Contact> getAllContactsByIds(List<Long> contactIds) {
        List<Contact> contacts = contactRepository.findAllById(contactIds);
        return contacts.stream()
                .collect(Collectors.toMap(Contact::getId, contact -> contact));
    }

    @Transactional
    public void save(Contact contact) {
        contactRepository.save(contact);
    }
}

