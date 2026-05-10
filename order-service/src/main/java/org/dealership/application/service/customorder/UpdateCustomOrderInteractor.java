package org.dealership.application.service.customorder;

import org.dealership.application.mapper.CustomOrderStatusMapper;
import org.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.dealership.application.port.in.customorder.dto.UpdateCustomOrderDto;
import org.dealership.application.port.out.messaging.OrderApprovalEventPort;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.dealership.events.OrderSentForApprovalEvent;
import org.dealership.events.PartRequirement;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UpdateCustomOrderInteractor implements UpdateCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CustomOrderStatusMapper statusMapper;
    private final OrderApprovalEventPort orderApprovalEventPort;

    public UpdateCustomOrderInteractor(
            CustomCarOrderRepository customOrderRepository,
            CustomOrderStatusMapper statusMapper,
            OrderApprovalEventPort orderApprovalEventPort
    ) {
        this.customOrderRepository = customOrderRepository;
        this.statusMapper = statusMapper;
        this.orderApprovalEventPort = orderApprovalEventPort;
    }

    @Override
    @Transactional
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
        if (newStatus == CustomCarOrderStatus.PAID) {
            List<PartRequirement> parts = order.getConfiguration()
                    .getComponentVariantSelection()
                    .getVariants()
                    .stream()
                    .map(this::toPartRequirement)
                    .toList();
            OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(
                    order.getId().value().toString(),
                    UUID.randomUUID().toString(),
                    Instant.now(),
                    "CUSTOM",
                    null,
                    order.getConfiguration().getModel().getId().value(),
                    parts
            );
            orderApprovalEventPort.publishSentForApproval(event);
        }
        return new Response();
    }

    private PartRequirement toPartRequirement(ComponentVariant variant) {
        return new PartRequirement(variant.getId().value(), 1);
    }
}
