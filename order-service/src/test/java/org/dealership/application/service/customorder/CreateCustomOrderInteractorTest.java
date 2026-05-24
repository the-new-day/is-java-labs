package org.dealership.application.service.customorder;

import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.port.in.customorder.CreateCustomOrderUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private ManagerProvider managerProvider;
    @Mock
    private UserSelectionStrategy userSelectionStrategy;
    @Mock
    private ConfigurationMapper configurationMapper;

    @Test
    void shouldCreateCustomOrder() {
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);
        UserId managerId = new UserId(UUID.randomUUID());

        when(carModelRepository.findById(new CarModelId(modelIdValue))).thenReturn(Optional.of(model));
        when(customOrderRepository.nextId()).thenReturn(new OrderId(UUID.randomUUID()));
        when(managerProvider.listManagerIds()).thenReturn(List.of(managerId));
        when(userSelectionStrategy.selectUser(List.of(managerId))).thenReturn(managerId);
        when(configurationMapper.toDomain(org.mockito.ArgumentMatchers.any()))
                .thenReturn(ServiceTestData.configuration(model));

        CreateCustomOrderInteractor interactor = new CreateCustomOrderInteractor(
                customOrderRepository,
                carModelRepository,
                managerProvider,
                userSelectionStrategy,
                configurationMapper
        );
        var response = interactor.execute(new CreateCustomOrderUseCase.Request(
                UUID.randomUUID(),
                ServiceTestData.configurationDto(modelIdValue)
        ));

        assertNotNull(response.id());
        verify(customOrderRepository).save(org.mockito.Mockito.any());
    }
}
