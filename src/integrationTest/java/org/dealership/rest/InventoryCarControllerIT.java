package org.dealership.rest;

import org.dealership.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class InventoryCarControllerIT extends AbstractIntegrationTest {

    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private MockMvc mockMvc;

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
