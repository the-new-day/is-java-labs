package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.GetCarUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.id.CarId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCarInteractorTest {
    @Mock
    private CarRepository carRepository;

    @Test
    void shouldGetCar() {
        UUID carIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        Car car = ServiceTestData.car(carIdValue, model);

        when(carRepository.findById(new CarId(carIdValue))).thenReturn(Optional.of(car));

        GetCarInteractor interactor = new GetCarInteractor(carRepository);
        var response = interactor.execute(new GetCarUseCase.Request(carIdValue));

        assertEquals(carIdValue, response.carDetails().id());
    }
}
