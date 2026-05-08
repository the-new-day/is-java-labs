package org.dealership.rest;

import org.dealership.AbstractIntegrationTest;
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
class TestDriveControllerIT extends AbstractIntegrationTest {

    private static final UUID REQUEST_ID = UUID.fromString("00000000-0000-0000-0000-000000000653");
    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTestDriveRequest_existing_returns200() throws Exception {
        mockMvc.perform(get("/api/test-drives/{id}", REQUEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest.id").value(REQUEST_ID.toString()))
                .andExpect(jsonPath("$.testDriveRequest.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.testDriveRequest.carId").value(CAR_ID.toString()));
    }

    @Test
    void getTestDriveRequest_nonExisting_returns404() throws Exception {
        mockMvc.perform(get("/api/test-drives/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listTestDriveRequests_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/test-drives"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest").isArray())
                .andExpect(jsonPath("$.testDriveRequest.length()").value(1));
    }

    @Test
    void createTestDriveRequest_validRequest_returns201WithLocationHeader() throws Exception {
        String requestBody = String.format(
                """
                {"clientId": "%s", "carId": "%s", "startsAt": "2026-07-01T10:00:00"}
                """,
                CLIENT_ID, CAR_ID
        );

        MvcResult result = mockMvc.perform(post("/api/test-drives")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/test-drives/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testDriveRequest.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.testDriveRequest.carId").value(CAR_ID.toString()));
    }

    @Test
    void updateTestDriveRequest_existingRequest_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "clientId": "%s", "carId": "%s", "startsAt": "2026-08-01T15:30:00"}
                """,
                REQUEST_ID, CLIENT_ID, CAR_ID
        );

        mockMvc.perform(put("/api/test-drives/{id}", REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTestDriveRequest_existing_returns204() throws Exception {
        mockMvc.perform(delete("/api/test-drives/{id}", REQUEST_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTestDriveRequest_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/test-drives/{id}", REQUEST_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/test-drives/{id}", REQUEST_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void setTestDriveAvailable_existingCar_returns200() throws Exception {
        mockMvc.perform(patch("/api/test-drives/cars/{carId}/availability", CAR_ID)
                        .param("available", "false"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/catalog/cars/{id}", CAR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails.testDriveAvailable").value(false));
    }
}
