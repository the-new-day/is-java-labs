package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderStatusMapper;
import org.dealership.application.port.in.stockorder.UpdateStockOrderUseCase;
import org.dealership.application.port.in.stockorder.dto.StockOrderStatusDto;
import org.dealership.application.port.in.stockorder.dto.UpdateStockOrderDto;
import org.dealership.application.port.out.messaging.OrderApprovalEventPort;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.StockCarOrder;
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
class UpdateStockOrderInteractorTest {
    @Mock
    private StockCarOrderRepository stockOrderRepository;
    @Mock
    private StockOrderStatusMapper stockOrderStatusMapper;
    @Mock
    private OrderApprovalEventPort orderApprovalEventPort;

    @Test
    void shouldUpdateStockOrderStatus() {
        UUID orderIdValue = UUID.randomUUID();
        StockCarOrder order = ServiceTestData.stockOrder(
                orderIdValue, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        when(stockOrderRepository.findById(new OrderId(orderIdValue)))
                .thenReturn(Optional.of(order));
        when(stockOrderStatusMapper.toDomain(any()))
                .thenReturn(StockCarOrderStatus.APPROVED_BY_MANAGER);

        UpdateStockOrderInteractor interactor = new UpdateStockOrderInteractor(stockOrderRepository, stockOrderStatusMapper, orderApprovalEventPort);
        UpdateStockOrderDto dto = new UpdateStockOrderDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                new StockOrderStatusDto("APPROVED_BY_MANAGER")
        );
        var response = interactor.execute(new UpdateStockOrderUseCase.Request(orderIdValue, dto));

        assertNotNull(response);
        verify(stockOrderRepository).save(any());
    }
}
