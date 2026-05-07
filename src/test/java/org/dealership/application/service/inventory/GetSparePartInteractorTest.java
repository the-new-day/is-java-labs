package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.GetSparePartUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSparePartInteractorTest {
    @Mock
    private SparePartRepository sparePartRepository;
    @Mock
    private SparePartMapper sparePartMapper;

    @Test
    void shouldGetSparePart() {
        UUID partIdValue = UUID.randomUUID();
        UUID modelIdValue = UUID.randomUUID();
        SparePart part = ServiceTestData.sparePart(partIdValue, modelIdValue);

        when(sparePartRepository.findById(new SparePartId(partIdValue))).thenReturn(Optional.of(part));
        when(sparePartMapper.toSummaryDto(part)).thenReturn(ServiceTestData.sparePartSummary(partIdValue, modelIdValue));

        GetSparePartInteractor interactor = new GetSparePartInteractor(sparePartRepository, sparePartMapper);
        var response = interactor.execute(new GetSparePartUseCase.Request(partIdValue));

        assertEquals(partIdValue, response.sparePartSummaryDto().id());
    }
}
