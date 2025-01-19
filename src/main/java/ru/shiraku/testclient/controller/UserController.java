package ru.shiraku.testclient.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shiraku.testclient.dto.ClientResponse;
import ru.shiraku.testclient.dto.CreateClient;
import ru.shiraku.testclient.dto.UpdateRequest;
import ru.shiraku.testclient.entity.Client;
import ru.shiraku.testclient.service.ClientService;
import ru.shiraku.testclient.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@Tag(name = "Клиенты", description = "Управление клиентами")
public class UserController {
    private final ClientService clientService;
    private final ContactService contactService;

    public UserController(ClientService clientService, ContactService contactService) {
        this.clientService = clientService;
        this.contactService = contactService;
    }

    @Operation(summary = "Регистрация нового клиента",
            description = "Регистрирует клиента на основе входных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Клиент успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    })
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody CreateClient client) {
        Long contact_id = contactService.createContact(client.getPhone(), client.getEmail());
        clientService.create(client, contact_id);
        return ResponseEntity.ok("Client created");
    }

    @Operation(summary = "Получение клиента",
            description = "Возвращает данные конкретного клиента на основе входных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Клиент успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    })
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    @Operation(summary = "Получение клиентов",
            description = "Возвращает список клиентов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Список успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    })
    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.getClients());
    }

    @Operation(summary = "Редактировать клиента",
            description = "Редактирование клиента на основе входных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Список успешно получен"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    })
    @PatchMapping("/{clientId}/update")
    public ResponseEntity<?> updateClient(@PathVariable Long clientId, @RequestBody UpdateRequest request) {
        clientService.update(clientId, request);
        return ResponseEntity.ok("Client updated");
    }

    @Operation(summary = "Удалить клиента",
            description = "Удалить клиента на основе входных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно удален"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClientById(clientId);
        return ResponseEntity.ok("Deleted");
    }


}
