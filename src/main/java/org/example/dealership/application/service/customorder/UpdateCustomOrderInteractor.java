package org.example.dealership.application.service.customorder;

import org.example.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.example.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.example.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.example.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.order.CustomCarOrder;
import org.example.dealership.domain.model.order.state.CustomCarOrderStatus;

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
        try {
            return CustomCarOrderStatus.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported custom order status: " + dto.name());
        }
    }
}
