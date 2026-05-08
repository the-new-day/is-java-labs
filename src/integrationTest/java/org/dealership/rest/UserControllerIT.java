package org.dealership.rest;

import org.dealership.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class UserControllerIT extends AbstractIntegrationTest {

    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUser_existingUser_returns200WithUserDetails() throws Exception {
        mockMvc.perform(get("/api/users/{id}", CLIENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.user.fullName").value("Ivan Petrov"))
                .andExpect(jsonPath("$.user.role.name").value("CLIENT"));
    }

    @Test
    void getUser_nonExistingUser_returns404() throws Exception {
        mockMvc.perform(get("/api/users/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listUsers_returns200WithUserList() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").isArray())
                .andExpect(jsonPath("$.user.length()").value(4));
    }

    @Test
    void createUser_validRequest_returns201WithLocationHeader() throws Exception {
        String requestBody = """
                {"fullName": "New Test User", "role": {"name": "CLIENT"}}
                """;

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/users/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).contains("id");
    }

    @Test
    void createUser_thenGetUser_returnsCreatedUser() throws Exception {
        String requestBody = """
                {"fullName": "Created User", "role": {"name": "MANAGER"}}
                """;

        MvcResult createResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String location = createResult.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.fullName").value("Created User"))
                .andExpect(jsonPath("$.user.role.name").value("MANAGER"));
    }

    @Test
    void updateUser_existingUser_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "fullName": "Updated Name", "role": {"name": "CLIENT"}}
                """,
                CLIENT_ID
        );

        mockMvc.perform(put("/api/users/{id}", CLIENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/{id}", CLIENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.fullName").value("Updated Name"));
    }

    @Test
    void deleteUser_existingUser_returns204() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", CLIENT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_thenGetUser_returns404() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", CLIENT_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{id}", CLIENT_ID))
                .andExpect(status().isNotFound());
    }
}
