package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.StockCarOrder;

import java.util.List;

public class ListStockOrdersInteractor implements ListStockOrdersUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final StockOrderMapper stockOrderMapper;

    public ListStockOrdersInteractor(StockCarOrderRepository stockOrderRepository, StockOrderMapper stockOrderMapper) {
        this.stockOrderRepository = stockOrderRepository;
        this.stockOrderMapper = stockOrderMapper;
    }

    @Override
    public Response execute(Request request) {
        List<StockCarOrder> orders = stockOrderRepository.findAll();
        return new Response(orders.stream().map(stockOrderMapper::toDto).toList());
    }
}
