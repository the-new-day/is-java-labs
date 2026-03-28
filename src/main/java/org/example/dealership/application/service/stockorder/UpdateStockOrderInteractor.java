package org.example.dealership.application.service.stockorder;

import org.example.dealership.application.mapping.StockOrderStatusMapper;
import org.example.dealership.application.port.in.stockorder.UpdateStockOrderUseCase;
import org.example.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.example.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.order.StockCarOrder;
import org.example.dealership.domain.model.order.state.StockCarOrderStatus;

public class UpdateStockOrderInteractor implements UpdateStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;

    public UpdateStockOrderInteractor(StockCarOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        StockOrderDto dto = request.order();
        OrderId orderId = new OrderId(dto.id());
        StockCarOrder order = stockOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Stock order not found: " + orderId));
        StockCarOrderStatus newStatus = StockOrderStatusMapper.mapFromDto(dto.status());
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new DomainValidationException(
                    "Invalid stock order status transition: " + order.getStatus() + " -> " + newStatus);
        }
        stockOrderRepository.save(order.withStatus(newStatus));
        return new Response();
    }
}
