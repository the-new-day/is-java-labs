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

    private static final UUID CLIENT_ID = SEED_CLIENT_ID;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUser_admin_returns200WithUserDetails() throws Exception {
        mockMvc.perform(get("/api/users/{id}", CLIENT_ID).with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.user.fullName").value("Ivan Petrov"));
    }

    @Test
    void getUser_self_returns200() throws Exception {
        mockMvc.perform(get("/api/users/{id}", CLIENT_ID).with(asClient()))
                .andExpect(status().isOk());
    }

    @Test
    void getUser_otherUser_returns403() throws Exception {
        mockMvc.perform(get("/api/users/{id}", CLIENT_ID).with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getUser_nonExistingUser_admin_returns404() throws Exception {
        mockMvc.perform(get("/api/users/{id}", UUID.randomUUID()).with(asAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listUsers_admin_returns200WithUserList() throws Exception {
        mockMvc.perform(get("/api/users").with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").isArray());
    }

    @Test
    void listUsers_clientForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/users").with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void createUser_admin_returns201() throws Exception {
        String requestBody = """
                {"username": "newuser", "password": "secret123", "fullName": "New Test User"}
                """;

        MvcResult result = mockMvc.perform(post("/api/users")
                        .with(asAdmin())
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
                {"username": "createduser", "password": "secret123", "fullName": "Created User"}
                """;

        MvcResult createResult = mockMvc.perform(post("/api/users")
                        .with(asAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String location = createResult.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location).with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.fullName").value("Created User"));
    }

    @Test
    void updateUser_admin_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "fullName": "Updated Name"}
                """,
                CLIENT_ID
        );

        mockMvc.perform(put("/api/users/{id}", CLIENT_ID)
                        .with(asAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/{id}", CLIENT_ID).with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.fullName").value("Updated Name"));
    }

    @Test
    void deleteUser_admin_returns204() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", CLIENT_ID).with(asAdmin()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_clientForbidden_returns403() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", CLIENT_ID).with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUser_thenGetUser_returns404() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", CLIENT_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/{id}", CLIENT_ID).with(asAdmin()))
                .andExpect(status().isNotFound());
    }
}
