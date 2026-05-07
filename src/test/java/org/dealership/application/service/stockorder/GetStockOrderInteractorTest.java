package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.port.in.stockorder.GetStockOrderUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.StockCarOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStockOrderInteractorTest {
    @Mock
    private StockCarOrderRepository stockOrderRepository;
    @Mock
    private StockOrderMapper stockOrderMapper;

    @Test
    void shouldGetStockOrder() {
        UUID orderIdValue = UUID.randomUUID();
        StockCarOrder order = ServiceTestData.stockOrder(
                orderIdValue,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        when(stockOrderRepository.findById(new OrderId(orderIdValue))).thenReturn(Optional.of(order));
        when(stockOrderMapper.toDto(order)).thenReturn(ServiceTestData.stockOrderDto(
                orderIdValue, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "PLACED"));

        GetStockOrderInteractor interactor = new GetStockOrderInteractor(stockOrderRepository, stockOrderMapper);
        var response = interactor.execute(new GetStockOrderUseCase.Request(orderIdValue));

        assertEquals(orderIdValue, response.order().id());
    }
}
