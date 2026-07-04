package org.dealership.application.service.carcatalog;

import org.dealership.application.mapper.CarFilterMapper;
import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCarsInteractorTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarFilterMapper carFilterMapper;
    @Mock
    private CarMapper carMapper;

    @Test
    void shouldListCarsUsingFilter() {
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        Car car = ServiceTestData.car(UUID.randomUUID(), model);

        when(carRepository.findByFilter(any())).thenReturn(List.of(car));

        CarFilterDto filterDto = ServiceTestData.carFilterDto(brand.getId().value(), model.getId().value());
        ListCarsInteractor interactor = new ListCarsInteractor(carRepository, carFilterMapper, carMapper);
        var response = interactor.execute(new ListCarsUseCase.Request(filterDto));

        assertEquals(1, response.carSummaryList().size());
    }
}
