package org.dealership.application.service.stockorder;

import org.dealership.application.port.in.stockorder.CreateStockOrderUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.dealership.domain.model.user.User;
import org.dealership.domain.model.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateStockOrderInteractorTest {
    @Mock
    private StockCarOrderRepository stockOrderRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSelectionStrategy userSelectionStrategy;

    @Test
    void shouldCreateStockOrder() {
        UUID carIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        Car car = ServiceTestData.car(carIdValue, model);
        User manager = ServiceTestData.user(UUID.randomUUID(), UserRole.MANAGER);

        when(carRepository.findById(new CarId(carIdValue))).thenReturn(Optional.of(car));
        when(stockOrderRepository.nextId()).thenReturn(new OrderId(UUID.randomUUID()));
        when(userRepository.findByRole(UserRole.MANAGER)).thenReturn(List.of(manager));
        when(userSelectionStrategy.selectUser(Stream.of(manager).map(User::getId).toList()))
                .thenReturn(manager.getId());

        CreateStockOrderInteractor interactor = new CreateStockOrderInteractor(
                stockOrderRepository,
                carRepository,
                userRepository,
                userSelectionStrategy
        );
        var response = interactor.execute(new CreateStockOrderUseCase.Request(
                UUID.randomUUID(),
                carIdValue
        ));

        assertNotNull(response.id());
        verify(stockOrderRepository).save(org.mockito.Mockito.any());
    }
}
