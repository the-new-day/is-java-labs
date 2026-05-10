package org.dealership.application.service.customorder;

import org.dealership.application.mapper.CustomOrderStatusMapper;
import org.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.dealership.application.port.in.customorder.dto.UpdateCustomOrderDto;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;

public class UpdateCustomOrderInteractor implements UpdateCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CustomOrderStatusMapper statusMapper;

    public UpdateCustomOrderInteractor(
            CustomCarOrderRepository customOrderRepository,
            CustomOrderStatusMapper statusMapper
    ) {
        this.customOrderRepository = customOrderRepository;
        this.statusMapper = statusMapper;
    }

    @Override
    public Response execute(Request request) {
        UpdateCustomOrderDto dto = request.order();
        OrderId orderId = new OrderId(request.customOrderId());
        CustomCarOrder order = customOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Custom order not found: " + orderId));
        CustomCarOrderStatus newStatus = statusMapper.toDomain(dto.status());
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new DomainValidationException(
                    "Invalid custom order status transition: " + order.getStatus() + " -> " + newStatus);
        }
        customOrderRepository.save(order.withStatus(newStatus));
        return new Response();
    }
}
