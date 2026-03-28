package org.dealership.application.service.customorder;

import org.dealership.application.port.in.customorder.GetCustomOrderUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCustomOrderInteractorTest {
    @Mock
    private CustomCarOrderRepository customOrderRepository;

    @Test
    void shouldGetCustomOrder() {
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

        GetCustomOrderInteractor interactor = new GetCustomOrderInteractor(customOrderRepository);
        var response = interactor.execute(new GetCustomOrderUseCase.Request(orderIdValue));

        assertEquals(orderIdValue, response.order().id());
    }
}
