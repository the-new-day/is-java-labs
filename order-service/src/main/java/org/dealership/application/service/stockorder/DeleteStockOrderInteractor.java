package org.dealership.application.service.stockorder;

import org.dealership.application.port.in.stockorder.DeleteStockOrderUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;

public class DeleteStockOrderInteractor implements DeleteStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;

    public DeleteStockOrderInteractor(StockCarOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        OrderId orderId = new OrderId(request.id());
        if (stockOrderRepository.findById(orderId).isEmpty()) {
            throw new EntityNotFoundException("Stock order not found: " + orderId);
        }
        stockOrderRepository.deleteById(orderId);
        return new Response();
    }
}
