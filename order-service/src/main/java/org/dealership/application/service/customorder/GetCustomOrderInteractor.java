package org.dealership.application.service.customorder;

import org.dealership.application.mapper.CustomOrderMapper;
import org.dealership.application.port.in.customorder.GetCustomOrderUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;

public class GetCustomOrderInteractor implements GetCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CustomOrderMapper customOrderMapper;

    public GetCustomOrderInteractor(
            CustomCarOrderRepository customOrderRepository,
            CustomOrderMapper customOrderMapper
    ) {
        this.customOrderRepository = customOrderRepository;
        this.customOrderMapper = customOrderMapper;
    }

    @Override
    public Response execute(Request request) {
        OrderId orderId = new OrderId(request.id());
        CustomCarOrder order = customOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Custom order not found: " + orderId));
        return new Response(customOrderMapper.toDto(order));
    }
}
