package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;

public class ListStockOrdersInteractor implements ListStockOrdersUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final StockOrderMapper stockOrderMapper;

    public ListStockOrdersInteractor(StockCarOrderRepository stockOrderRepository, StockOrderMapper stockOrderMapper) {
        this.stockOrderRepository = stockOrderRepository;
        this.stockOrderMapper = stockOrderMapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                stockOrderRepository.findAll().stream()
                        .map(stockOrderMapper::toDto)
                        .toList()
        );
    }
}
