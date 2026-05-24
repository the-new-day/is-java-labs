package org.dealership.rest;

import org.dealership.AbstractControllerIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarCatalogControllerIT extends AbstractControllerIT {

    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");
    private static final UUID MODEL_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");
    private static final UUID BRAND_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCar_existingCar_returns200WithCarDetails() throws Exception {
        mockMvc.perform(get("/api/catalog/cars/{id}", CAR_ID).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carDetails").exists())
                .andExpect(jsonPath("$.carDetails.id").value(CAR_ID.toString()))
                .andExpect(jsonPath("$.carDetails.testDriveAvailable").value(true));
    }

    @Test
    void getCar_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/catalog/cars/{id}", CAR_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCar_nonExistingCar_returns404() throws Exception {
        mockMvc.perform(get("/api/catalog/cars/{id}", UUID.randomUUID()).with(asClient()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listCars_returns200WithCarList() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList").isArray())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void getModel_existingModel_returns200WithModelDetails() throws Exception {
        mockMvc.perform(get("/api/catalog/models/{id}", MODEL_ID)
                        .with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").exists())
                .andExpect(jsonPath("$.model.id").value(MODEL_ID.toString()))
                .andExpect(jsonPath("$.model.modelName").value("320i"));
    }

    @Test
    void getModel_nonExistingModel_returns404() throws Exception {
        mockMvc.perform(get("/api/catalog/models/{id}", UUID.randomUUID()).with(asClient()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listModels_returns200WithModelList() throws Exception {
        mockMvc.perform(get("/api/catalog/models")
                        .with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelSummaryList").isArray())
                .andExpect(jsonPath("$.modelSummaryList.length()").value(3));
    }

    @Test
    void listCars_filterByMatchingColor_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars")
                        .with(asClient()).param("color", "BLACK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByNonMatchingColor_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars")
                        .with(asClient())
                        .param("color", "WHITE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByBrandId_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars")
                        .with(asClient())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByModelId_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("modelId", MODEL_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByBodyType_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("bodyType", "SEDAN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByNonMatchingBodyType_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("bodyType", "SUV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByFuelType_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("fuelType", "PETROL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByDriveType_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("driveType", "REAR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByEnginePowerRange_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient())
                        .param("minEnginePower", "180")
                        .param("maxEnginePower", "200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByTooHighMinEnginePower_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("minEnginePower", "300"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByEngineVolumeRange_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient())
                        .param("minEngineVolume", "1.5")
                        .param("maxEngineVolume", "2.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByTooLowMaxEngineVolume_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("maxEngineVolume", "1.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByCombinedMatchingParams_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient())
                        .param("color", "BLACK")
                        .param("bodyType", "SEDAN")
                        .param("fuelType", "PETROL")
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByCombinedNonMatchingParams_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient())
                        .param("color", "WHITE")
                        .param("bodyType", "SEDAN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByTransmissionType_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("transmissionType", "AUTOMATIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByNonMatchingTransmissionType_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("transmissionType", "MANUAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByMinPrice_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("minPrice", "3000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByMinPriceAboveCarPrice_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("minPrice", "4000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByMaxPrice_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("maxPrice", "4000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }

    @Test
    void listCars_filterByMaxPriceBelowCarPrice_returnsEmpty() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient()).param("maxPrice", "3000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(0));
    }

    @Test
    void listCars_filterByPriceRange_returnsOne() throws Exception {
        mockMvc.perform(get("/api/catalog/cars").with(asClient())
                        .param("minPrice", "3000000")
                        .param("maxPrice", "4000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carSummaryList.length()").value(1));
    }
}
