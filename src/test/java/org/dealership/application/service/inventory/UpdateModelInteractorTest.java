package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.port.in.inventory.UpdateModelUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarModelId;
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
class UpdateModelInteractorTest {
    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private CarModelMapper carModelMapper;

    @Test
    void shouldUpdateModel() {
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);

        when(carModelRepository.findById(new CarModelId(modelIdValue))).thenReturn(Optional.of(model));

        UpdateModelInteractor interactor = new UpdateModelInteractor(carModelRepository, carModelMapper);
        var response = interactor.execute(new UpdateModelUseCase.Request(ServiceTestData.carModelDto(modelIdValue)));

        assertNotNull(response);
        verify(carModelRepository).save(org.mockito.Mockito.any());
    }
}
