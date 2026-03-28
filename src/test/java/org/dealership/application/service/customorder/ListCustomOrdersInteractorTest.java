package org.dealership.application.service.customorder;

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

    @Test
    void shouldListCustomOrders() {
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);
        CustomCarOrder order = ServiceTestData.customOrder(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                model
        );

        when(customOrderRepository.findAll()).thenReturn(List.of(order));

        ListCustomOrdersInteractor interactor = new ListCustomOrdersInteractor(customOrderRepository);
        var response = interactor.execute(new ListCustomOrdersUseCase.Request());

        assertEquals(1, response.order().size());
    }
}
