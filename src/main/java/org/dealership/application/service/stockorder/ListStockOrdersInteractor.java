package org.dealership.application.service.stockorder;

import org.dealership.application.mapping.StockOrderMapper;
import org.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;

public class ListStockOrdersInteractor implements ListStockOrdersUseCase {
    private final StockCarOrderRepository stockOrderRepository;

    public ListStockOrdersInteractor(StockCarOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                stockOrderRepository.findAll().stream()
                        .map(StockOrderMapper::mapToDto)
                        .toList()
        );
    }
}
