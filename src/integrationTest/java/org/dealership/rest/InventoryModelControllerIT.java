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
class InventoryModelControllerIT extends AbstractIntegrationTest {

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

    private static String newModelJson(String name) {
        return String.format("""
                {
                  "brandId": "00000000-0000-0000-0000-000000000001",
                  "name": "%s",
                  "basePrice": {"amount": 4000000.00},
                  "carBodyType": {"name": "SEDAN"},
                  "fuelType": {"name": "PETROL"},
                  "driveType": {"name": "REAR"},
                  "engineVolume": 2.0,
                  "enginePower": 218,
                  "baseTransmissionType": {"name": "AUTOMATIC"},
                  "baseComponentSelection": {"selection": {"WHEELS": %s, "TRANSMISSION": %s, "STEERING_WHEEL": %s, "INTERIOR": %s}},
                  "configurableComponentTypes": [{"name":"WHEELS"},{"name":"TRANSMISSION"},{"name":"STEERING_WHEEL"},{"name":"INTERIOR"}]
                }""",
                name,
                VARIANT_WHEELS, VARIANT_TRANSMISSION, VARIANT_STEERING, VARIANT_INTERIOR);
    }

    @Test
    void addModel_warehouseAdmin_returns201() throws Exception {
        String requestBody = newModelJson("325i");

        MvcResult result = mockMvc.perform(post("/api/inventory/models")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/inventory/models/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location.replace("/api/inventory/models/", "/api/catalog/models/")).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model.modelName").value("325i"));
    }

    @Test
    void addModel_clientForbidden_returns403() throws Exception {
        mockMvc.perform(post("/api/inventory/models")
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newModelJson("325i")))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateModel_warehouseAdmin_returns200() throws Exception {
        String requestBody = newModelJson("320i Updated");

        mockMvc.perform(put("/api/inventory/models/{id}", MODEL_ID)
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void deleteModel_admin_returns204() throws Exception {
        mockMvc.perform(delete("/api/inventory/models/{id}", MODEL_ID).with(asAdmin()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteModel_warehouseAdminForbidden_returns403() throws Exception {
        mockMvc.perform(delete("/api/inventory/models/{id}", MODEL_ID).with(asWarehouseAdmin()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteModel_thenCatalogGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/inventory/models/{id}", MODEL_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/catalog/models/{id}", MODEL_ID).with(asClient()))
                .andExpect(status().isNotFound());
    }
}
