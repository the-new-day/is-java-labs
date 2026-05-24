package org.dealership.application.service.customorder;

import org.dealership.application.mapper.CustomOrderMapper;
import org.dealership.application.port.in.customorder.ListCustomOrdersUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.order.CustomCarOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCustomOrdersInteractorTest {
    @Mock
    private CustomCarOrderRepository customOrderRepository;
    @Mock
    private CustomOrderMapper customOrderMapper;

    @Test
    void shouldListCustomOrders() {
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
        var dto = ServiceTestData.customOrderDto(orderIdValue, UUID.randomUUID(), UUID.randomUUID(), modelIdValue, "PLACED");

        when(customOrderRepository.findAll()).thenReturn(List.of(order));
        when(customOrderMapper.toDto(order)).thenReturn(dto);

        ListCustomOrdersInteractor interactor = new ListCustomOrdersInteractor(customOrderRepository, customOrderMapper);
        var response = interactor.execute(new ListCustomOrdersUseCase.Request());

        assertEquals(1, response.order().size());
    }
}
