package ru.shiraku.testclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shiraku.testclient.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
