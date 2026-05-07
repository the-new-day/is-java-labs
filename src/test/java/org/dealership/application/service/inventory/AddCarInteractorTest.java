package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.inventory.AddCarUseCase;
import org.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarId;
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
class AddCarInteractorTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private CarMapper carMapper;

    @Test
    void shouldAddCar() {
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);

        when(carModelRepository.findById(new CarModelId(modelIdValue))).thenReturn(Optional.of(model));
        when(carRepository.nextId()).thenReturn(new CarId(UUID.randomUUID()));

        AddCarInteractor interactor = new AddCarInteractor(carRepository, carModelRepository, carMapper);
        NewCarDetailsDto newCar = ServiceTestData.newCarDetailsDto(modelIdValue);
        var response = interactor.execute(new AddCarUseCase.Request(newCar));

        assertNotNull(response.id());
        verify(carRepository).save(org.mockito.Mockito.any());
    }
}
