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
class InventorySparePartControllerIT extends AbstractIntegrationTest {

    private static final UUID SPARE_PART_ID = UUID.fromString("00000000-0000-0000-0000-000000000551");
    private static final UUID MODEL_320I_ID = UUID.fromString("00000000-0000-0000-0000-000000000101");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSparePart_existing_returns200() throws Exception {
        mockMvc.perform(get("/api/inventory/spare-parts/{id}", SPARE_PART_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sparePartSummaryDto.id").value(SPARE_PART_ID.toString()))
                .andExpect(jsonPath("$.sparePartSummaryDto.name").value("BMW Brake Pads Set"));
    }

    @Test
    void getSparePart_nonExisting_returns404() throws Exception {
        mockMvc.perform(get("/api/inventory/spare-parts/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listSpareParts_returns200WithList() throws Exception {
        mockMvc.perform(get("/api/inventory/spare-parts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sparePartSummaryDtoList").isArray())
                .andExpect(jsonPath("$.sparePartSummaryDtoList.length()").value(1));
    }

    @Test
    void addSparePart_validRequest_returns201WithLocationHeader() throws Exception {
        String requestBody = String.format(
                """
                {"name": "Wiper Blades", "price": {"amount": 4500.00}, "compatibleModelIds": ["%s"]}
                """,
                MODEL_320I_ID
        );

        MvcResult result = mockMvc.perform(post("/api/inventory/spare-parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("/api/inventory/spare-parts/")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).isNotNull();

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sparePartSummaryDto.name").value("Wiper Blades"));
    }

    @Test
    void updateSparePart_existing_returns200() throws Exception {
        String requestBody = String.format(
                """
                {"id": "%s", "name": "BMW Brake Pads Set Premium", "price": {"amount": 32000.00}, "compatibleModelIds": ["%s"]}
                """,
                SPARE_PART_ID, MODEL_320I_ID
        );

        mockMvc.perform(put("/api/inventory/spare-parts/{id}", SPARE_PART_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/inventory/spare-parts/{id}", SPARE_PART_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sparePartSummaryDto.name").value("BMW Brake Pads Set Premium"));
    }

    @Test
    void deleteSparePart_existing_returns204() throws Exception {
        mockMvc.perform(delete("/api/inventory/spare-parts/{id}", SPARE_PART_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSparePart_thenGet_returns404() throws Exception {
        mockMvc.perform(delete("/api/inventory/spare-parts/{id}", SPARE_PART_ID))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/inventory/spare-parts/{id}", SPARE_PART_ID))
                .andExpect(status().isNotFound());
    }
}
