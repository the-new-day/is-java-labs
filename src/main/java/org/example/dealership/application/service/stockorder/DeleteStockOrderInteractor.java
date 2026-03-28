package org.example.dealership.application.service.stockorder;

import org.example.dealership.application.port.in.stockorder.DeleteStockOrderUseCase;
import org.example.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.OrderId;

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
