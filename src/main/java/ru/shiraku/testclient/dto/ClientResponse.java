package ru.shiraku.testclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponse {
    @Schema(description = "Уникальный идентификатор клиента")
    private Long client_id;
    @Schema(description = "Имя клиента", example = "Vladimir")
    private String name;
    @Schema(description = "Фамилия клиента", example = "Dostoevsky")
    private String last_name;
    @Schema(description = "Номер телефона клиента")
    private String phone;
    @Schema(description = "Электронная почта клиента")
    private String email;
}
