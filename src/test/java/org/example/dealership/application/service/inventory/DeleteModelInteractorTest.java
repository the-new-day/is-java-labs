package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.DeleteModelUseCase;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.car.CarModel;
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
class DeleteModelInteractorTest {
    @Mock
    private CarModelRepository carModelRepository;

    @Test
    void shouldDeleteModel() {
        UUID modelIdValue = UUID.randomUUID();
        when(carModelRepository.findById(new CarModelId(modelIdValue)))
                .thenReturn(Optional.of(mock(CarModel.class)));

        DeleteModelInteractor interactor = new DeleteModelInteractor(carModelRepository);
        var response = interactor.execute(new DeleteModelUseCase.Request(modelIdValue));

        assertNotNull(response);
        verify(carModelRepository).deleteById(new CarModelId(modelIdValue));
    }
}
