package ru.shiraku.testclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.shiraku.testclient.controller.UserController;
import ru.shiraku.testclient.dto.ClientResponse;
import ru.shiraku.testclient.dto.CreateClient;
import ru.shiraku.testclient.dto.UpdateRequest;
import ru.shiraku.testclient.entity.Client;
import ru.shiraku.testclient.service.ClientService;
import ru.shiraku.testclient.service.ContactService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private ClientService clientService;

    @Mock
    private ContactService contactService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registration_shouldReturnCreated() throws Exception {
        CreateClient client = new CreateClient("John", "Doe", "123456789", "john.doe@example.com");

        when(contactService.createContact(client.getPhone(), client.getEmail())).thenReturn(1L);
        doNothing().when(clientService).create(client, 1L);
        String requestBody = """
                {
                    "name": "John",
                    "last_name": "Doe",
                    "email": "john.doe@example.com",
                    "phone": "123456789"
                }
                """;

        mockMvc.perform(post("/api/client/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Client created"));

        verify(contactService, times(1)).createContact(client.getPhone(), client.getEmail());
        verify(clientService, times(1)).create(client, 1L);
    }

    @Test
    void getClient_shouldReturnClient() throws Exception {
        Long clientId = 1L;
        Client client = new Client(1L, "John", "Doe", 1L);

        when(clientService.getClientById(clientId)).thenReturn(client);

        mockMvc.perform(get("/api/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client_id").value(1L))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.last_name").value("Doe"));

        verify(clientService, times(1)).getClientById(clientId);
    }

    @Test
    void getAllClients_shouldReturnClientList() throws Exception {
        List<ClientResponse> mockClients = List.of(
                new ClientResponse(1L, "John", "Doe", "john.doe@example.com", "123456789"),
                new ClientResponse(2L, "Jane", "Smith", "jane.smith@example.com", "987654321")
        );

        when(clientService.getClients()).thenReturn(mockClients);

        mockMvc.perform(get("/api/client")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(clientService, times(1)).getClients();
    }

    @Test
    void updateClient_shouldReturnUpdatedMessage() throws Exception {
        Long clientId = 1L;
        UpdateRequest updateRequest = new UpdateRequest("New Name", "New Last Name", null, null);

        doNothing().when(clientService).update(eq(clientId), any(UpdateRequest.class));

        String requestBody = """
                {
                    "name": "New Name",
                    "lastName": "New Last Name"
                }
                """;

        mockMvc.perform(patch("/api/client/{clientId}/update", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Client updated"));

        verify(clientService, times(1)).update(eq(clientId), any(UpdateRequest.class));
    }

    @Test
    void deleteClient_shouldReturnDeletedMessage() throws Exception {
        Long clientId = 1L;

        doNothing().when(clientService).deleteClientById(clientId);

        mockMvc.perform(delete("/api/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        verify(clientService, times(1)).deleteClientById(clientId);
    }
}
