package org.dealership.application.service.carcatalog;

import org.dealership.application.port.in.carcatalog.GetModelUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetModelInteractorTest {
    @Mock
    private CarModelRepository carModelRepository;

    @Test
    void shouldGetModel() {
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);

        when(carModelRepository.findById(new CarModelId(modelIdValue))).thenReturn(Optional.of(model));

        GetModelInteractor interactor = new GetModelInteractor(carModelRepository);
        var response = interactor.execute(new GetModelUseCase.Request(modelIdValue));

        assertEquals(modelIdValue, response.model().id());
        verify(carModelRepository).findById(new CarModelId(modelIdValue));
    }
}
