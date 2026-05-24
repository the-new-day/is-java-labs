package org.dealership.rest;

import org.dealership.AbstractControllerIT;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssemblyOrderControllerIT extends AbstractControllerIT {

    static final UUID SOURCE_ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000110");
    static final UUID SEED_CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");
    static final UUID SEED_PART_ID = UUID.fromString("00000000-0000-0000-0000-000000000205");

    @Autowired
    private MockMvc mockMvc;

    private String newAssemblyOrderJson(UUID sourceOrderId, String status) {
        return """
                {
                  "sourceOrderId": "%s",
                  "sourceOrderType": "IN_STOCK",
                  "carId": "%s",
                  "carModelId": null,
                  "requiredParts": [{"partId": "%s", "quantity": 2}],
                  "status": "%s",
                  "assignedWorkerId": null
                }
                """.formatted(sourceOrderId, SEED_CAR_ID, SEED_PART_ID, status);
    }

    @Test
    void listAssemblyOrders_warehouseAdmin_returns200() throws Exception {
        mockMvc.perform(get("/api/assembly-orders").with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assemblyOrders").isArray());
    }

    @Test
    void listAssemblyOrders_clientForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/assembly-orders").with(asClient()))
                .andExpect(status().isForbidden());
    }

    @Test
    void listAssemblyOrders_managerForbidden_returns403() throws Exception {
        mockMvc.perform(get("/api/assembly-orders").with(asManager()))
                .andExpect(status().isForbidden());
    }

    @Test
    void listAssemblyOrders_unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/assembly-orders"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createAssemblyOrder_warehouseAdmin_returns201() throws Exception {
        mockMvc.perform(post("/api/assembly-orders")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "CREATED")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void createAssemblyOrder_admin_returns201() throws Exception {
        mockMvc.perform(post("/api/assembly-orders")
                        .with(asAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "CREATED")))
                .andExpect(status().isCreated());
    }

    @Test
    void createAssemblyOrder_clientForbidden_returns403() throws Exception {
        mockMvc.perform(post("/api/assembly-orders")
                        .with(asClient())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "CREATED")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAssemblyOrder_existingOrder_returns200() throws Exception {
        String locationHeader = mockMvc.perform(post("/api/assembly-orders")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "CREATED")))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        String id = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

        mockMvc.perform(get("/api/assembly-orders/" + id).with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assemblyOrder.id").value(id))
                .andExpect(jsonPath("$.assemblyOrder.status").value("CREATED"));
    }

    @Test
    void getAssemblyOrder_nonExisting_returns404() throws Exception {
        mockMvc.perform(get("/api/assembly-orders/" + UUID.randomUUID()).with(asWarehouseAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAssemblyOrder_warehouseAdmin_returns200() throws Exception {
        String locationHeader = mockMvc.perform(post("/api/assembly-orders")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "CREATED")))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        String id = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

        mockMvc.perform(put("/api/assembly-orders/" + id)
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "ASSEMBLED")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/assembly-orders/" + id).with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assemblyOrder.status").value("ASSEMBLED"));
    }

    @Test
    void deleteAssemblyOrder_admin_returns204() throws Exception {
        String locationHeader = mockMvc.perform(post("/api/assembly-orders")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(UUID.randomUUID(), "CREATED")))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        String id = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

        mockMvc.perform(delete("/api/assembly-orders/" + id).with(asAdmin()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/assembly-orders/" + id).with(asWarehouseAdmin()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAndListAssemblyOrders_returnsCreatedOrder() throws Exception {
        UUID sourceOrderId = UUID.randomUUID();

        mockMvc.perform(post("/api/assembly-orders")
                        .with(asWarehouseAdmin())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAssemblyOrderJson(sourceOrderId, "CREATED")))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/assembly-orders").with(asWarehouseAdmin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assemblyOrders[*].sourceOrderId",
                        Matchers.hasItem(sourceOrderId.toString())));
    }
}
