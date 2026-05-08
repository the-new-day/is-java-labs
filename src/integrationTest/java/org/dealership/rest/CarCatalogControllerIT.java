package org.dealership.rest;

import org.dealership.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarCatalogControllerIT extends AbstractIntegrationTest {

    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");
    private static final UUID MODEL_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCar_existingCar_returns200WithCarDetails() throws Exception {
        mockMvc.perform(get("/api/catalog/cars/{id}", CAR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails").exists())
                .andExpect(jsonPath("$.carDetails.id").value(CAR_ID.toString()))
                .andExpect(jsonPath("$.carDetails.testDriveAvailable").value(true));
    }

    @Test
    void getCar_nonExistingCar_returns404() throws Exception {
        mockMvc.perform(get("/api/catalog/cars/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listCars_returns200WithCarList() throws Exception {
        mockMvc.perform(get("/api/catalog/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList").isArray())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void getModel_existingModel_returns200WithModelDetails() throws Exception {
        mockMvc.perform(get("/api/catalog/models/{id}", MODEL_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").exists())
                .andExpect(jsonPath("$.model.id").value(MODEL_ID.toString()))
                .andExpect(jsonPath("$.model.modelName").value("320i"));
    }

    @Test
    void getModel_nonExistingModel_returns404() throws Exception {
        mockMvc.perform(get("/api/catalog/models/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listModels_returns200WithModelList() throws Exception {
        mockMvc.perform(get("/api/catalog/models"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelSummaryList").isArray())
                .andExpect(jsonPath("$.modelSummaryList.length()").value(3));
    }
}
