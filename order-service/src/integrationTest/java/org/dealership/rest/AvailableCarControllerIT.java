package org.dealership.rest;

import org.dealership.AbstractControllerIT;
import org.dealership.application.port.in.inventory.dto.AvailableCarDto;
import org.dealership.application.port.out.inventory.AvailableCarsPort;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.exception.StorageServiceUnavailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AvailableCarControllerIT extends AbstractControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailableCarsPort availableCarsPort;

    private AvailableCarDto sampleCar() {
        return new AvailableCarDto(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Toyota",
                "Camry",
                "WHITE",
                new BigDecimal("25000.00")
        );
    }

    @Test
    void listCars_asUser_returns200() throws Exception {
        when(availableCarsPort.listAvailableCars()).thenReturn(List.of(sampleCar()));

        mockMvc.perform(get("/api/v1/cars").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cars").isArray())
                .andExpect(jsonPath("$.cars[0].brandName").value("Toyota"));
    }

    @Test
    void listCars_asManager_returns200() throws Exception {
        when(availableCarsPort.listAvailableCars()).thenReturn(List.of(sampleCar()));

        mockMvc.perform(get("/api/v1/cars").with(asManager()))
                .andExpect(status().isOk());
    }

    @Test
    void listCars_asAdmin_returns200() throws Exception {
        when(availableCarsPort.listAvailableCars()).thenReturn(List.of(sampleCar()));

        mockMvc.perform(get("/api/v1/cars").with(asAdmin()))
                .andExpect(status().isOk());
    }

    @Test
    void listCars_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listCars_asWarehouseAdmin_returns403() throws Exception {
        mockMvc.perform(get("/api/v1/cars").with(asWarehouseAdmin()))
                .andExpect(status().isForbidden());
    }

    @Test
    void listCars_emptyInventory_returnsEmptyList() throws Exception {
        when(availableCarsPort.listAvailableCars()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/cars").with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cars").isArray())
                .andExpect(jsonPath("$.cars").isEmpty());
    }

    @Test
    void listCars_storageServiceUnavailable_returns503() throws Exception {
        when(availableCarsPort.listAvailableCars())
                .thenThrow(new StorageServiceUnavailableException("Storage service down"));

        mockMvc.perform(get("/api/v1/cars").with(asClient()))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getCar_existingCar_returns200() throws Exception {
        UUID carId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(availableCarsPort.getAvailableCar(carId)).thenReturn(sampleCar());

        mockMvc.perform(get("/api/v1/cars/" + carId).with(asClient()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.car.brandName").value("Toyota"))
                .andExpect(jsonPath("$.car.modelName").value("Camry"));
    }

    @Test
    void getCar_notFound_returns404() throws Exception {
        UUID randomId = UUID.randomUUID();
        when(availableCarsPort.getAvailableCar(any()))
                .thenThrow(new EntityNotFoundException("Car not found: " + randomId));

        mockMvc.perform(get("/api/v1/cars/" + randomId).with(asClient()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCar_storageServiceUnavailable_returns503() throws Exception {
        when(availableCarsPort.getAvailableCar(any()))
                .thenThrow(new StorageServiceUnavailableException("Storage service down"));

        mockMvc.perform(get("/api/v1/cars/" + UUID.randomUUID()).with(asClient()))
                .andExpect(status().isServiceUnavailable());
    }
}
