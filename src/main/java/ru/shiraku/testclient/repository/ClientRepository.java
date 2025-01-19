package ru.shiraku.testclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shiraku.testclient.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
