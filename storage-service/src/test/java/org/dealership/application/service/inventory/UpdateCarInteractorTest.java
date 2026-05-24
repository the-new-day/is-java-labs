package org.dealership.application.service.inventory;

import org.dealership.application.mapper.ColorMapper;
import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.port.in.inventory.UpdateCarUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.enums.Color;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCarInteractorTest {
    @Mock private CarRepository carRepository;
    @Mock private CarModelRepository carModelRepository;
    @Mock private ConfigurationMapper configurationMapper;
    @Mock private ColorMapper colorMapper;

    @Test
    void shouldUpdateCar() {
        UUID carIdValue = UUID.randomUUID();
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);
        Car existing = ServiceTestData.car(carIdValue, model);

        when(carRepository.findById(new CarId(carIdValue))).thenReturn(Optional.of(existing));
        when(carModelRepository.findById(new CarModelId(modelIdValue))).thenReturn(Optional.of(model));
        when(configurationMapper.toDomain(any())).thenReturn(ServiceTestData.configuration(model));
        when(colorMapper.toDomain(any())).thenReturn(Color.BLACK);

        UpdateCarInteractor interactor = new UpdateCarInteractor(carRepository, carModelRepository, configurationMapper, colorMapper);
        var response = interactor.execute(new UpdateCarUseCase.Request(carIdValue, ServiceTestData.newCarDetailsDto(modelIdValue)));

        assertNotNull(response);
        verify(carRepository).save(any());
    }
}
