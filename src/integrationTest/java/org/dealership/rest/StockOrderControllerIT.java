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
    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getStockOrder_existingOrder_returns200() throws Exception {
        mockMvc.perform(get("/api/stock-orders/{id}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.id").value(ORDER_ID.toString()))
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.order.carId").value(CAR_ID.toString()))
                .andExpect(jsonPath("$.order.status.name").value("PLACED"));
    }

    @Test
    void getStockOrder_nonExistingOrder_returns404() throws Exception {
        mockMvc.perform(get("/api/stock-orders/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listStockOrders_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/stock-orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void createStockOrder_validRequest_returns201WithLocationHeader() throws Exception {
        String requestBody = String.format(
                """
                {"clientId": "%s", "carId": "%s"}
                """,
                CLIENT_ID, CAR_ID
        );

        MvcResult result = mockMvc.perform(post("/api/stock-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/stock-orders/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()))
                .andExpect(jsonPath("$.order.carId").value(CAR_ID.toString()));
    }

    @Test
    void updateStockOrder_existingOrder_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "clientId": "%s", "managerId": "%s", "carId": "%s", "status": {"name": "CONFIRMED"}}
                """,
                ORDER_ID, CLIENT_ID, "00000000-0000-0000-0000-000000000302", CAR_ID
        );

        mockMvc.perform(put("/api/stock-orders/{id}", ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStockOrder_existingOrder_returns204() throws Exception {
        mockMvc.perform(delete("/api/stock-orders/{id}", ORDER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStockOrder_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/stock-orders/{id}", ORDER_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/stock-orders/{id}", ORDER_ID))
                .andExpect(status().isNotFound());
    }
}
