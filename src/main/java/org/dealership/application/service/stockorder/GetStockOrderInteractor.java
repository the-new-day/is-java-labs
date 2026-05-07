package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.port.in.stockorder.GetStockOrderUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;

public class GetStockOrderInteractor implements GetStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final StockOrderMapper stockOrderMapper;

    public GetStockOrderInteractor(StockCarOrderRepository stockOrderRepository, StockOrderMapper stockOrderMapper) {
        this.stockOrderRepository = stockOrderRepository;
        this.stockOrderMapper = stockOrderMapper;
    }

    @Override
    public Response execute(Request request) {
        OrderId orderId = new OrderId(request.id());
        return stockOrderRepository.findById(orderId)
                .map(stockOrderMapper::toDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Stock order not found: " + orderId));
    }
}
