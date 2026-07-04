package org.dealership.application.service.customorder;

import org.dealership.application.mapper.CustomOrderMapper;
import org.dealership.application.port.in.customorder.ListCustomOrdersUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.model.order.CustomCarOrder;

import java.util.List;

public class ListCustomOrdersInteractor implements ListCustomOrdersUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CustomOrderMapper customOrderMapper;

    public ListCustomOrdersInteractor(
            CustomCarOrderRepository customOrderRepository,
            CustomOrderMapper customOrderMapper
    ) {
        this.customOrderRepository = customOrderRepository;
        this.customOrderMapper = customOrderMapper;
    }

    @Override
    public Response execute(Request request) {
        List<CustomCarOrder> orders = customOrderRepository.findAll();
        return new Response(orders.stream().map(customOrderMapper::toDto).toList());
    }
}
