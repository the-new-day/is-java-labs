package org.dealership.application.service.stockorder;

import org.dealership.application.port.in.stockorder.UpdateStockOrderUseCase;
import org.dealership.application.port.in.stockorder.dto.StockOrderDto;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateStockOrderInteractorTest {
    @Mock
    private StockCarOrderRepository stockOrderRepository;

    @Test
    void shouldUpdateStockOrderStatus() {
        UUID orderIdValue = UUID.randomUUID();
        StockCarOrder order = ServiceTestData.stockOrder(
                orderIdValue, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        when(stockOrderRepository.findById(new OrderId(orderIdValue)))
                .thenReturn(Optional.of(order));

        UpdateStockOrderInteractor interactor = new UpdateStockOrderInteractor(stockOrderRepository);
        StockOrderDto dto = ServiceTestData.stockOrderDto(
                orderIdValue,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "APPROVED_BY_MANAGER"
        );
        var response = interactor.execute(new UpdateStockOrderUseCase.Request(dto));

        assertNotNull(response);
        verify(stockOrderRepository).save(org.mockito.Mockito.any());
    }
}
