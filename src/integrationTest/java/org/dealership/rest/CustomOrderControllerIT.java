package org.dealership.rest;

import org.dealership.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class CustomOrderControllerIT extends AbstractIntegrationTest {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000652");
    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCustomOrder_existingOrder_returns200() throws Exception {
        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.id").value(ORDER_ID.toString()))
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.order.managerId").value(MANAGER_ID.toString()))
                .andExpect(jsonPath("$.order.status.name").value("PLACED"));
    }

    @Test
    void getCustomOrder_nonExistingOrder_returns404() throws Exception {
        mockMvc.perform(get("/api/custom-orders/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listCustomOrders_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/custom-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void updateCustomOrder_existingOrder_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "clientId": "%s", "managerId": "%s", "carId": "%s", "status": {"name": "CONFIRMED"}}
                """,
                ORDER_ID, CLIENT_ID, MANAGER_ID, CAR_ID
        );

        mockMvc.perform(put("/api/custom-orders/{id}", ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomOrder_existingOrder_returns204() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomOrder_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID))
                .andExpect(status().isNotFound());
    }
}
