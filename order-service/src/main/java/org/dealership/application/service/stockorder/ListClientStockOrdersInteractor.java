package org.dealership.application.service.stockorder;

import org.dealership.application.mapper.BaseIdMapper;
import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.port.in.stockorder.ListClientStockOrdersUseCase;
import org.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.StockCarOrder;

import java.util.List;

public class ListClientStockOrdersInteractor implements ListClientStockOrdersUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final StockOrderMapper stockOrderMapper;
    private final BaseIdMapper idMapper;

    public ListClientStockOrdersInteractor(
            StockCarOrderRepository stockOrderRepository,
            StockOrderMapper stockOrderMapper,
            BaseIdMapper idMapper
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.stockOrderMapper = stockOrderMapper;
        this.idMapper = idMapper;
    }

    @Override
    public Response execute(Request request) {
        List<StockCarOrder> orders = stockOrderRepository
                .findByClientId(idMapper.toUserId(request.clientId()));
        return new Response(orders.stream().map(stockOrderMapper::toDto).toList());
    }
}
