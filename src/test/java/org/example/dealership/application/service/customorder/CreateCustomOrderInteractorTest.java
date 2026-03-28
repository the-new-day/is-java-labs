package org.example.dealership.application.service.customorder;

import org.example.dealership.application.port.in.customorder.CreateCustomOrderUseCase;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.example.dealership.application.port.out.persistence.UserRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.order.UserSelectionStrategy;
import org.example.dealership.domain.model.user.User;
import org.example.dealership.domain.model.user.UserRole;
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
class CreateCustomOrderInteractorTest {
    @Mock
    private CustomCarOrderRepository customOrderRepository;
    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSelectionStrategy userSelectionStrategy;

    @Test
    void shouldCreateCustomOrder() {
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);
        User manager = ServiceTestData.user(UUID.randomUUID(), UserRole.MANAGER);

        when(carModelRepository.findById(new CarModelId(modelIdValue))).thenReturn(Optional.of(model));
        when(customOrderRepository.nextId()).thenReturn(new OrderId(UUID.randomUUID()));
        when(userRepository.findByRole(UserRole.MANAGER)).thenReturn(List.of(manager));
        when(userSelectionStrategy.selectUser(Stream.of(manager).map(User::getId).toList()))
                .thenReturn(manager.getId());

        CreateCustomOrderInteractor interactor = new CreateCustomOrderInteractor(
                customOrderRepository,
                carModelRepository,
                userRepository,
                userSelectionStrategy
        );
        var response = interactor.execute(new CreateCustomOrderUseCase.Request(
                UUID.randomUUID(),
                ServiceTestData.configurationDto(modelIdValue)
        ));

        assertNotNull(response.id());
        verify(customOrderRepository).save(org.mockito.Mockito.any());
    }
}
