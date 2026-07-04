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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class CustomOrderControllerIT extends AbstractControllerIT {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000652");
    private static final UUID CLIENT_ID = SEED_CLIENT_ID;
    private static final UUID MANAGER_ID = SEED_MANAGER_ID;
    private static final UUID MODEL_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    private static final UUID BRAND_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    private MockMvc mockMvc;

    private static final String VARIANT_WHEELS = """
            {"id":"00000000-0000-0000-0000-000000000201","type":{"name":"WHEELS"},"name":"17 Standard","surcharge":{"amount":0.00},"carModelIds":["00000000-0000-0000-0000-000000000101"]}""";
    private static final String VARIANT_TRANSMISSION = """
            {"id":"00000000-0000-0000-0000-000000000202","type":{"name":"TRANSMISSION"},"name":"Automatic 8AT","surcharge":{"amount":0.00},"carModelIds":["00000000-0000-0000-0000-000000000101","00000000-0000-0000-0000-000000000102"]}""";
    private static final String VARIANT_STEERING = """
            {"id":"00000000-0000-0000-0000-000000000203","type":{"name":"STEERING_WHEEL"},"name":"Sport Leather Standard","surcharge":{"amount":0.00},"carModelIds":["00000000-0000-0000-0000-000000000101","00000000-0000-0000-0000-000000000102"]}""";
    private static final String VARIANT_INTERIOR = """
            {"id":"00000000-0000-0000-0000-000000000204","type":{"name":"INTERIOR"},"name":"Graphite Fabric","surcharge":{"amount":0.00},"carModelIds":["00000000-0000-0000-0000-000000000101"]}""";

    private static String configurationJson() {
        return String.format("""
                {
                  "carModel": {
                    "id": "00000000-0000-0000-0000-000000000101",
                    "brand": {"id": "00000000-0000-0000-0000-000000000001", "name": "BMW"},
                    "modelName": "320i",
                    "basePrice": {"amount": 3500000.00},
                    "carBodyType": {"name": "SEDAN"},
                    "fuelType": {"name": "PETROL"},
                    "driveType": {"name": "REAR"},
                    "engineVolume": 2.0,
                    "enginePower": 184,
                    "baseTransmissionType": {"name": "AUTOMATIC"},
                    "baseComponentSelection": {"selection": {"WHEELS": %s, "TRANSMISSION": %s, "STEERING_WHEEL": %s, "INTERIOR": %s}},
                    "configurableComponentTypes": [{"name":"WHEELS"},{"name":"TRANSMISSION"},{"name":"STEERING_WHEEL"},{"name":"INTERIOR"}]
                  },
                  "componentVariantSelection": {"selection": {"WHEELS": %s, "TRANSMISSION": %s, "STEERING_WHEEL": %s, "INTERIOR": %s}}
                }""",
                VARIANT_WHEELS, VARIANT_TRANSMISSION, VARIANT_STEERING, VARIANT_INTERIOR,
                VARIANT_WHEELS, VARIANT_TRANSMISSION, VARIANT_STEERING, VARIANT_INTERIOR);
    }

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
    void createCustomOrder_client_returns201() throws Exception {
        String requestBody = String.format("""
                {"configuration": %s}""", configurationJson());

        MvcResult result = mockMvc.perform(post("/api/custom-orders")
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/custom-orders/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order.clientId").value(CLIENT_ID.toString()));
    }

    @Test
    void createCustomOrder_managerForbidden_returns403() throws Exception {
        String requestBody = String.format("""
                {"configuration": %s}""", configurationJson());

        mockMvc.perform(post("/api/custom-orders")
                        .with(asManager())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void listCustomOrders_managerSeesAll_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/custom-orders").with(asManager()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void listCustomOrders_clientForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/custom-orders").with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void listCustomOrders_strangerClientForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/custom-orders").with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listMyCustomOrders_client_returns200WithOwnOrders() throws Exception {
        mockMvc.perform(get("/api/custom-orders/my").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(1));
    }

    @Test
    void listMyCustomOrders_strangerClient_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/custom-orders/my").with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order").isArray())
                .andExpect(jsonPath("$.order.length()").value(0));
    }

    @Test
    void listMyCustomOrders_managerForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/custom-orders/my").with(asManager()))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateCustomOrder_managerCanUpdate_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"clientId": "%s", "managerId": "%s", "status": {"name": "APPROVED_BY_WAREHOUSE"}}
                """,
                CLIENT_ID, MANAGER_ID
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
                {"clientId": "%s", "managerId": "%s", "status": {"name": "APPROVED_BY_WAREHOUSE"}}
                """,
                CLIENT_ID, MANAGER_ID
        );

        mockMvc.perform(put("/api/custom-orders/{id}", ORDER_ID)
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCustomOrder_clientForbidden_returns403() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCustomOrder_otherUserForbidden_returns403() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(jwtUser(UUID.randomUUID(), "USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCustomOrder_admin_returns204() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomOrder_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/custom-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/custom-orders/{id}", ORDER_ID).with(asAdmin()))
                .andExpect(status().isNotFound());
    }
}
