package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.DeleteCarUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.CarId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCarInteractorTest {
    @Mock private CarRepository carRepository;

    @Test
    void shouldDeleteCar() {
        UUID carIdValue = UUID.randomUUID();
        when(carRepository.findById(new CarId(carIdValue))).thenReturn(Optional.of(mock(Car.class)));

        DeleteCarInteractor interactor = new DeleteCarInteractor(carRepository);
        var response = interactor.execute(new DeleteCarUseCase.Request(carIdValue));

        assertNotNull(response);
        verify(carRepository).deleteById(new CarId(carIdValue));
    }
}
