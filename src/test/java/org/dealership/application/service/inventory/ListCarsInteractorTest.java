package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.inventory.ListInventoryCarsUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCarsInteractorTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;

    @Test
    void shouldListCars() {
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        Car car = ServiceTestData.car(UUID.randomUUID(), model);

        when(carRepository.findAll()).thenReturn(List.of(car));

        ListInventoryCarsInteractor interactor = new ListInventoryCarsInteractor(carRepository, carMapper);
        var response = interactor.execute(new ListInventoryCarsUseCase.Request());

        assertEquals(1, response.carSummaryList().size());
    }
}
