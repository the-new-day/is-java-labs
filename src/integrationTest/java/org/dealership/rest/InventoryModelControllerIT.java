package org.dealership.rest;

import org.dealership.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class InventoryModelControllerIT extends AbstractIntegrationTest {

    private static final UUID MODEL_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteModel_existingModel_returns204() throws Exception {
        mockMvc.perform(delete("/api/inventory/models/{id}", MODEL_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteModel_thenCatalogGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/inventory/models/{id}", MODEL_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/catalog/models/{id}", MODEL_ID))
                .andExpect(status().isNotFound());
    }
}
