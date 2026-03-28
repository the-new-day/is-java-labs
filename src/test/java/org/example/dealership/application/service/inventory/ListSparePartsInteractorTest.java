package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.ListSparePartsUseCase;
import org.example.dealership.application.port.out.persistence.SparePartRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.part.SparePart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListSparePartsInteractorTest {
    @Mock
    private SparePartRepository sparePartRepository;

    @Test
    void shouldListSpareParts() {
        SparePart part = ServiceTestData.sparePart(UUID.randomUUID(), UUID.randomUUID());
        when(sparePartRepository.findAll()).thenReturn(List.of(part));

        ListSparePartsInteractor interactor = new ListSparePartsInteractor(sparePartRepository);
        var response = interactor.execute(new ListSparePartsUseCase.Request());

        assertEquals(1, response.sparePartSummaryList().size());
    }
}
