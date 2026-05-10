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
class InventoryCarControllerIT extends AbstractIntegrationTest {

    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

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

    private static String newCarJson(String vin, String color) {
        return String.format("""
                {
                  "vinNumber": {"value": "%s"},
                  "configuration": {
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
                  },
                  "color": {"name": "%s"}
                }""",
                vin,
                VARIANT_WHEELS, VARIANT_TRANSMISSION, VARIANT_STEERING, VARIANT_INTERIOR,
                VARIANT_WHEELS, VARIANT_TRANSMISSION, VARIANT_STEERING, VARIANT_INTERIOR,
                color);
    }

    @Test
    void getCar_warehouseAdmin_returns200WithCarDetails() throws Exception {
        mockMvc.perform(get("/api/inventory/cars/{id}", CAR_ID).with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails.id").value(CAR_ID.toString()))
                .andExpect(jsonPath("$.carDetails.testDriveAvailable").value(true))
                .andExpect(jsonPath("$.carDetails.color.name").value("BLACK"));
    }

    @Test
    void getCar_clientForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/inventory/cars/{id}", CAR_ID).with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCar_nonExistingCar_returns404() throws Exception {
        mockMvc.perform(get("/api/inventory/cars/{id}", UUID.randomUUID()).with(asWarehouseAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listCars_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/inventory/cars").with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList").isArray())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void addCar_warehouseAdmin_returns201() throws Exception {
        String requestBody = newCarJson("WBA8E9G50JNU99999", "WHITE");

        MvcResult result = mockMvc.perform(post("/api/inventory/cars")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/inventory/cars/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location).with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails.color.name").value("WHITE"));
    }

    @Test
    void updateCar_warehouseAdmin_returns200() throws Exception {
        String requestBody = newCarJson("WBA8E9G50JNU12345", "RED");

        mockMvc.perform(put("/api/inventory/cars/{id}", CAR_ID)
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/inventory/cars/{id}", CAR_ID).with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails.color.name").value("RED"));
    }

    @Test
    void addCar_clientForbidden_returns403() throws Exception {
        String requestBody = newCarJson("WBA8E9G50JNU88888", "BLACK");

        mockMvc.perform(post("/api/inventory/cars")
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCar_admin_returns204() throws Exception {
        mockMvc.perform(delete("/api/inventory/cars/{id}", CAR_ID).with(asAdmin()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCar_warehouseAdminForbidden_returns403() throws Exception {
        mockMvc.perform(delete("/api/inventory/cars/{id}", CAR_ID).with(asWarehouseAdmin()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteCar_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/inventory/cars/{id}", CAR_ID).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/inventory/cars/{id}", CAR_ID).with(asWarehouseAdmin()))
                .andExpect(status().isNotFound());
    }
}
