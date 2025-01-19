package ru.shiraku.testclient.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Schema(description = "Модель контактов")
@Table(name = "contacts")
public class Contact {
    @Id
    @Column(name = "contact_id", updatable = false, nullable = false)
    @Schema(description = "Уникальный идентификатор контакта")
    private Long id;
    @Column(name = "phone", nullable = false)
    @Schema(description = "Номер телефона клиента")
    private String phone;
    @Column(name = "email", nullable = false)
    @Schema(description = "Электронная почта клиента")
    private String email;
}
