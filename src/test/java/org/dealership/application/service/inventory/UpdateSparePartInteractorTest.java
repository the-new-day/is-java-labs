package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.UpdateSparePartUseCase;
import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateSparePartInteractorTest {
    @Mock
    private SparePartRepository sparePartRepository;
    @Mock
    private SparePartMapper sparePartMapper;

    @Test
    void shouldUpdateSparePart() {
        UUID partIdValue = UUID.randomUUID();
        UUID modelIdValue = UUID.randomUUID();
        SparePart part = ServiceTestData.sparePart(partIdValue, modelIdValue);

        when(sparePartRepository.findById(new SparePartId(partIdValue))).thenReturn(Optional.of(part));

        UpdateSparePartInteractor interactor = new UpdateSparePartInteractor(sparePartRepository, sparePartMapper);
        SparePartSummaryDto summary = ServiceTestData.sparePartSummary(partIdValue, modelIdValue);
        var response = interactor.execute(new UpdateSparePartUseCase.Request(summary));

        assertNotNull(response);
        verify(sparePartRepository).save(org.mockito.Mockito.any());
    }
}
