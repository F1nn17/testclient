package ru.shiraku.testclient.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@Schema(description = "Модель клиента")
@Table(name = "clients")
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id", updatable = false, nullable = false)
    @Schema(description = "Уникальный идентификатор клиента")
    private Long client_id;
    @Column(name = "name", nullable = false)
    @Schema(description = "Имя клиента", example = "Vladimir")
    private String name;
    @Column(name = "last_name", nullable = false)
    @Schema(description = "Фамилия клиента", example = "Dostoevsky")
    private String last_name;
    @Column(name = "contact_id", updatable = false, nullable = false)
    @Schema(description = "Уникальный идентификатор контакта клиента", example = "1")
    private Long contact_id;
}
