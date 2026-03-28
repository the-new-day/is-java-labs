package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.DeleteSparePartUseCase;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.application.service.inventory.DeleteSparePartInteractor;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteSparePartInteractorTest {
    @Mock
    private SparePartRepository sparePartRepository;

    @Test
    void shouldDeleteSparePart() {
        UUID partIdValue = UUID.randomUUID();
        when(sparePartRepository.findById(new SparePartId(partIdValue)))
                .thenReturn(Optional.of(mock(SparePart.class)));

        DeleteSparePartInteractor interactor = new DeleteSparePartInteractor(sparePartRepository);
        var response = interactor.execute(new DeleteSparePartUseCase.Request(partIdValue));

        assertNotNull(response);
        verify(sparePartRepository).deleteById(new SparePartId(partIdValue));
    }
}
