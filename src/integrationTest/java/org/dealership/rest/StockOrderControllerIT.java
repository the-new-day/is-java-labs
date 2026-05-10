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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class StockOrderControllerIT extends AbstractIntegrationTest {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000651");
    private static final UUID CLIENT_ID = SEED_CLIENT_ID;
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getStockOrder_existingOrder_returns200() throws Exception {
        mockMvc.perform(get("/api/stock-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.id").value(ORDER_ID.toString()))
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.order.carId").value(CAR_ID.toString()))
                .andExpect(jsonPath("$.order.status.name").value("PLACED"));
    }

    @Test
    void getStockOrder_nonExistingOrder_returns404() throws Exception {
        mockMvc.perform(get("/api/stock-orders/{id}", UUID.randomUUID()).with(asAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStockOrder_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/stock-orders/{id}", ORDER_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listStockOrders_managerSeesAll_returns200() throws Exception {
        mockMvc.perform(get("/api/stock-orders").with(asManager()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void listStockOrders_clientForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/stock-orders").with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void listMyStockOrders_client_returns200WithOwnOrders() throws Exception {
        mockMvc.perform(get("/api/stock-orders/my").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void listMyStockOrders_strangerClient_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/stock-orders/my").with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(0));
    }

    @Test
    void listMyStockOrders_managerForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/stock-orders/my").with(asManager()))
                .andExpect(status().isForbidden());
    }

    @Test
    void createStockOrder_clientCanCreate_returns201() throws Exception {
        String requestBody = String.format("""
                {"carId": "%s"}
                """, CAR_ID);

        MvcResult result = mockMvc.perform(post("/api/stock-orders")
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/stock-orders/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.order.carId").value(CAR_ID.toString()));
    }

    @Test
    void createStockOrder_adminForbidden_returns403() throws Exception {
        String requestBody = String.format("""
                {"carId": "%s"}
                """, CAR_ID);

        mockMvc.perform(post("/api/stock-orders")
                        .with(asAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateStockOrder_managerCanUpdate_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"clientId": "%s", "managerId": "%s", "carId": "%s", "status": {"name": "APPROVED_BY_MANAGER"}}
                """,
                CLIENT_ID, SEED_MANAGER_ID, CAR_ID
        );

        mockMvc.perform(put("/api/stock-orders/{id}", ORDER_ID)
                        .with(asManager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStockOrder_clientForbidden_returns403() throws Exception {
        mockMvc.perform(delete("/api/stock-orders/{id}", ORDER_ID).with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteStockOrder_admin_returns204() throws Exception {
        mockMvc.perform(delete("/api/stock-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStockOrder_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/stock-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/stock-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNotFound());
    }
}
