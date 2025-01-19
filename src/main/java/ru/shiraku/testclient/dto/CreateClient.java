package ru.shiraku.testclient.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Модель регистрации клиента")
public class CreateClient {
    @Schema(description = "Имя клиента", example = "Vladimir")
    @NotBlank(message = "Имя клиента не должно быть пустым.")
    private String name;
    @Schema(description = "Фамилия клиента", example = "Dostoevsky")
    @NotBlank(message = "Фамилия клиента не должно быть пустым.")
    private String last_name;
    @Schema(description = "Номер телефона клиента", example = "+7(999)000-12-54")
    @NotBlank(message = "Номер телефона клиента не должен быть пустым.")
    private String phone;
    @Schema(description = "Электронная почта клиента", example = "vladimir.d@example.com")
    @NotBlank(message = "Электронная почта клиента не должна быть пустой.")
    private String email;
}
