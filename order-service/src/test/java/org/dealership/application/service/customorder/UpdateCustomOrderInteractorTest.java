package org.dealership.application.service.customorder;

import org.dealership.application.mapper.CustomOrderStatusMapper;
import org.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.dealership.application.port.in.customorder.dto.UpdateCustomOrderDto;
import org.dealership.application.port.out.messaging.OrderApprovalEventPort;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
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
class UpdateCustomOrderInteractorTest {
    @Mock
    private CustomCarOrderRepository customOrderRepository;
    @Mock
    private CustomOrderStatusMapper statusMapper;
    @Mock
    private OrderApprovalEventPort orderApprovalEventPort;

    @Test
    void shouldUpdateCustomOrderStatus() {
        UUID orderIdValue = UUID.randomUUID();
        UUID modelIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(modelIdValue, brand);
        CustomCarOrder order = ServiceTestData.customOrder(
                orderIdValue,
                UUID.randomUUID(),
                UUID.randomUUID(),
                model
        );

        when(customOrderRepository.findById(new OrderId(orderIdValue))).thenReturn(Optional.of(order));
        when(statusMapper.toDomain(any())).thenReturn(CustomCarOrderStatus.APPROVED_BY_WAREHOUSE);

        UpdateCustomOrderInteractor interactor = new UpdateCustomOrderInteractor(customOrderRepository, statusMapper, orderApprovalEventPort);
        UpdateCustomOrderDto updateDto = new UpdateCustomOrderDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                ServiceTestData.configurationDto(modelIdValue),
                new CustomOrderStatusDto("APPROVED_BY_WAREHOUSE")
        );
        var response = interactor.execute(new UpdateCustomOrderUseCase.Request(orderIdValue, updateDto));

        assertNotNull(response);
        verify(customOrderRepository).save(any());
    }
}
