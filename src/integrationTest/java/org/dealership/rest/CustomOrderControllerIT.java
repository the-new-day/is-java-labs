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
    private static final UUID CLIENT_ID = SEED_CLIENT_ID;
    private static final UUID MANAGER_ID = SEED_MANAGER_ID;
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCustomOrder_existingOrder_returns200() throws Exception {
        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.id").value(ORDER_ID.toString()))
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.order.managerId").value(MANAGER_ID.toString()))
                .andExpect(jsonPath("$.order.status.name").value("PLACED"));
    }

    @Test
    void getCustomOrder_nonExistingOrder_returns404() throws Exception {
        mockMvc.perform(get("/api/custom-orders/{id}", UUID.randomUUID()).with(asAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomOrder_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCustomOrder_otherUser_returns403() throws Exception {
        UUID otherUser = UUID.randomUUID();
        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID).with(jwtUser(otherUser, "USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCustomOrder_owner_returns200() throws Exception {
        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID).with(asClient()))
                .andExpect(status().isOk());
    }

    @Test
    void listCustomOrders_managerSeesAll_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/custom-orders").with(asManager()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void listCustomOrders_clientSeesOnlyOwn_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/custom-orders").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void listCustomOrders_strangerClient_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/custom-orders").with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.length()").value(0));
    }

    @Test
    void updateCustomOrder_managerCanUpdate_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "clientId": "%s", "managerId": "%s", "carId": "%s", "status": {"name": "CONFIRMED"}}
                """,
                ORDER_ID, CLIENT_ID, MANAGER_ID, CAR_ID
        );

        mockMvc.perform(put("/api/custom-orders/{id}", ORDER_ID)
                        .with(asManager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomOrder_clientForbidden_returns403() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "clientId": "%s", "managerId": "%s", "carId": "%s", "status": {"name": "CONFIRMED"}}
                """,
                ORDER_ID, CLIENT_ID, MANAGER_ID, CAR_ID
        );

        mockMvc.perform(put("/api/custom-orders/{id}", ORDER_ID)
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCustomOrder_owner_returns204() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(asClient()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomOrder_otherUser_returns403() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCustomOrder_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNotFound());
    }
}
