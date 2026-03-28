package org.example.dealership.application.service.carcatalog;

import org.example.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.example.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.Car;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.carfilter.CarFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCarsInteractorTest {
    @Mock
    private CarRepository carRepository;

    @Test
    void shouldListCarsUsingFilter() {
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        Car car = ServiceTestData.car(UUID.randomUUID(), model);

        when(carRepository.find(any())).thenReturn(List.of(car));

        CarFilterDto filterDto = ServiceTestData.carFilterDto(brand.getId().value(), model.getId().value());
        ListCarsInteractor interactor = new ListCarsInteractor(carRepository);
        var response = interactor.execute(new ListCarsUseCase.Request(filterDto));

        assertEquals(1, response.carSummaryList().size());
        ArgumentCaptor<CarFilter> captor = ArgumentCaptor.forClass(CarFilter.class);
        verify(carRepository).find(captor.capture());
        assertNotNull(captor.getValue());
    }
}
