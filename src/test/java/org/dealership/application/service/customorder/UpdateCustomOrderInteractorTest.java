package org.dealership.application.service.customorder;

import org.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCustomOrderInteractorTest {
    @Mock
    private CustomCarOrderRepository customOrderRepository;

    @Test
    void shouldUpdateCustomOrderStatus() {
        UUID orderIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        CustomCarOrder order = ServiceTestData.customOrder(
                orderIdValue,
                UUID.randomUUID(),
                UUID.randomUUID(),
                model
        );

        when(customOrderRepository.findById(new OrderId(orderIdValue))).thenReturn(Optional.of(order));

        UpdateCustomOrderInteractor interactor = new UpdateCustomOrderInteractor(customOrderRepository);
        CustomOrderDto dto = ServiceTestData.customOrderDto(
                orderIdValue,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "APPROVED_BY_WAREHOUSE");
        var response = interactor.execute(new UpdateCustomOrderUseCase.Request(dto));

        assertNotNull(response);
        verify(customOrderRepository).save(org.mockito.Mockito.any());
    }
}
