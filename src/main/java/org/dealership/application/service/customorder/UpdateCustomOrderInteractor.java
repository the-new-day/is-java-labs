package org.dealership.application.service.customorder;

import org.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;

public class UpdateCustomOrderInteractor implements UpdateCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;

    public UpdateCustomOrderInteractor(CustomCarOrderRepository customOrderRepository) {
        this.customOrderRepository = customOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        CustomOrderDto dto = request.order();
        OrderId orderId = new OrderId(dto.id());
        CustomCarOrder order = customOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Custom order not found: " + orderId));
        CustomCarOrderStatus newStatus = mapStatus(dto.status());
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new DomainValidationException(
                    "Invalid custom order status transition: " + order.getStatus() + " -> " + newStatus);
        }
        customOrderRepository.save(order.withStatus(newStatus));
        return new Response();
    }

    private static CustomCarOrderStatus mapStatus(CustomOrderStatusDto dto) {
        String name = dto.name() == null ? null : dto.name().toUpperCase();
        if ("CONFIRMED".equals(name)) {
            return CustomCarOrderStatus.APPROVED_BY_WAREHOUSE;
        }
        try {
            return CustomCarOrderStatus.valueOf(name);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported custom order status: " + dto.name());
        }
    }
}
