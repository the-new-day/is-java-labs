package org.dealership.rest;

import org.dealership.AbstractControllerIT;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class TestDriveControllerIT extends AbstractControllerIT {

    private static final UUID REQUEST_ID = UUID.fromString("00000000-0000-0000-0000-000000000653");
    private static final UUID CLIENT_ID = SEED_CLIENT_ID;
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTestDriveRequest_admin_returns200() throws Exception {
        mockMvc.perform(get("/api/test-drives/{id}", REQUEST_ID).with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest.id").value(REQUEST_ID.toString()))
                .andExpect(jsonPath("$.testDriveRequest.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.testDriveRequest.carId").value(CAR_ID.toString()));
    }

    @Test
    void getTestDriveRequest_nonExisting_returns404() throws Exception {
        mockMvc.perform(get("/api/test-drives/{id}", UUID.randomUUID()).with(asAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTestDriveRequest_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/test-drives/{id}", REQUEST_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listTestDriveRequests_managerSeesAll_returns200() throws Exception {
        mockMvc.perform(get("/api/test-drives").with(asManager()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest").isArray())
                .andExpect(jsonPath("$.testDriveRequest.length()").value(1));
    }

    @Test
    void listMyTestDriveRequests_client_returns200WithOwnRequests() throws Exception {
        mockMvc.perform(get("/api/test-drives/my").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest").isArray())
                .andExpect(jsonPath("$.testDriveRequest.length()").value(1));
    }

    @Test
    void listMyTestDriveRequests_strangerClient_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/test-drives/my").with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest").isArray())
                .andExpect(jsonPath("$.testDriveRequest.length()").value(0));
    }

    @Test
    void listMyTestDriveRequests_managerForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/test-drives/my").with(asManager()))
                .andExpect(status().isForbidden());
    }

    @Test
    void createTestDriveRequest_clientCanCreate_returns201() throws Exception {
        String requestBody = String.format("""
                {"carId": "%s", "startsAt": "2026-07-01T10:00:00"}
                """, CAR_ID);

        MvcResult result = mockMvc.perform(post("/api/test-drives")
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/test-drives/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.testDriveRequest.carId").value(CAR_ID.toString()));
    }

    @Test
    void updateTestDriveRequest_managerCanUpdate_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "clientId": "%s", "carId": "%s", "startsAt": "2026-08-01T15:30:00"}
                """,
                REQUEST_ID, CLIENT_ID, CAR_ID
        );

        mockMvc.perform(put("/api/test-drives/{id}", REQUEST_ID)
                        .with(asManager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTestDriveRequest_owner_returns204() throws Exception {
        mockMvc.perform(delete("/api/test-drives/{id}", REQUEST_ID).with(asClient()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTestDriveRequest_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/test-drives/{id}", REQUEST_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/test-drives/{id}", REQUEST_ID).with(asAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void setTestDriveAvailable_manager_returns200() throws Exception {
        mockMvc.perform(patch("/api/test-drives/cars/{carId}/availability", CAR_ID)
                        .with(asManager())
                        .param("available", "false"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/catalog/cars/{id}", CAR_ID).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails.testDriveAvailable").value(false));
    }

    @Test
    void setTestDriveAvailable_clientForbidden_returns403() throws Exception {
        mockMvc.perform(patch("/api/test-drives/cars/{carId}/availability", CAR_ID)
                        .with(asClient())
                        .param("available", "false"))
                .andExpect(status().isForbidden());
    }
}
