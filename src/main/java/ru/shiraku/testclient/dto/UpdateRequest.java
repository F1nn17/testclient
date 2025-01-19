package ru.shiraku.testclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Модель обновления клиента")
public class UpdateRequest {
    @Schema(description = "Имя клиента", example = "Vova")
    private String name;
    @Schema(description = "Фамилия клиента", example = "NeDostoevsky")
    private String lastName;
    @Schema(description = "Номер телефона клиента", example = "+7(999)111-12-54")
    private String phone;
    @Schema(description = "Электронная почта клиента", example = "vova.net@example.com")
    private String email;
}
