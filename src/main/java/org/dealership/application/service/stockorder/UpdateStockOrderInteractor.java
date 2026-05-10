package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderStatusMapper;
import org.dealership.application.port.in.stockorder.UpdateStockOrderUseCase;
import org.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.dealership.application.port.in.stockorder.dto.UpdateStockOrderDto;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.StockCarOrder;
import org.dealership.domain.model.order.state.StockCarOrderStatus;

public class UpdateStockOrderInteractor implements UpdateStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final StockOrderStatusMapper stockOrderStatusMapper;

    public UpdateStockOrderInteractor(StockCarOrderRepository stockOrderRepository, StockOrderStatusMapper stockOrderStatusMapper) {
        this.stockOrderRepository = stockOrderRepository;
        this.stockOrderStatusMapper = stockOrderStatusMapper;
    }

    @Override
    public Response execute(Request request) {
        UpdateStockOrderDto dto = request.order();
        OrderId orderId = new OrderId(request.orderId());
        StockCarOrder order = stockOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Stock order not found: " + orderId));
        StockCarOrderStatus newStatus = stockOrderStatusMapper.toDomain(dto.status());
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new DomainValidationException(
                    "Invalid stock order status transition: " + order.getStatus() + " -> " + newStatus);
        }
        stockOrderRepository.save(order.withStatus(newStatus));
        return new Response();
    }
}
