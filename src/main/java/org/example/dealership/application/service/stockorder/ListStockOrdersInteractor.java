package org.example.dealership.application.service.stockorder;

import org.example.dealership.application.mapping.StockOrderMapper;
import org.example.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.example.dealership.application.port.out.persistence.StockCarOrderRepository;

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
