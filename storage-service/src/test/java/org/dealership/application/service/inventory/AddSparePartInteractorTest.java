package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.AddSparePartUseCase;
import org.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.SparePartId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddSparePartInteractorTest {
    @Mock private SparePartRepository sparePartRepository;
    @Mock private SparePartMapper sparePartMapper;

    @Test
    void shouldAddSparePart() {
        UUID partIdValue = UUID.randomUUID();
        UUID modelIdValue = UUID.randomUUID();

        when(sparePartRepository.nextId()).thenReturn(new SparePartId(partIdValue));

        AddSparePartInteractor interactor = new AddSparePartInteractor(sparePartRepository, sparePartMapper);
        NewSparePartDto dto = ServiceTestData.newSparePartDto(modelIdValue);
        var response = interactor.execute(new AddSparePartUseCase.Request(dto));

        assertNotNull(response.id());
        verify(sparePartRepository).save(org.mockito.Mockito.any());
    }
}
