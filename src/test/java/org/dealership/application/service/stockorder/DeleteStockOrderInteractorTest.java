package org.dealership.application.service.stockorder;

import org.dealership.application.port.in.stockorder.DeleteStockOrderUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.service.stockorder.DeleteStockOrderInteractor;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.StockCarOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteStockOrderInteractorTest {
    @Mock
    private StockCarOrderRepository stockOrderRepository;

    @Test
    void shouldDeleteStockOrder() {
        UUID orderIdValue = UUID.randomUUID();
        when(stockOrderRepository.findById(new OrderId(orderIdValue)))
                .thenReturn(Optional.of(mock(StockCarOrder.class)));

        DeleteStockOrderInteractor interactor = new DeleteStockOrderInteractor(stockOrderRepository);
        var response = interactor.execute(new DeleteStockOrderUseCase.Request(orderIdValue));

        assertNotNull(response);
        verify(stockOrderRepository).deleteById(new OrderId(orderIdValue));
    }
}
