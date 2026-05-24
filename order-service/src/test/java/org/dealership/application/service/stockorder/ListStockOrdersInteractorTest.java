package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.order.StockCarOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListStockOrdersInteractorTest {
    @Mock
    private StockCarOrderRepository stockOrderRepository;
    @Mock
    private StockOrderMapper stockOrderMapper;

    @Test
    void shouldListStockOrders() {
        StockCarOrder order = ServiceTestData.stockOrder(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
        when(stockOrderRepository.findAll()).thenReturn(List.of(order));

        ListStockOrdersInteractor interactor = new ListStockOrdersInteractor(stockOrderRepository, stockOrderMapper);
        var response = interactor.execute(new ListStockOrdersUseCase.Request());

        assertEquals(1, response.order().size());
    }
}
