package org.example.dealership.application.service.customorder;

import org.example.dealership.application.port.in.customorder.GetCustomOrderUseCase;
import org.example.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.example.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.example.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.order.CustomCarOrder;

public class GetCustomOrderInteractor implements GetCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;

    public GetCustomOrderInteractor(CustomCarOrderRepository customOrderRepository) {
        this.customOrderRepository = customOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        OrderId orderId = new OrderId(request.id());
        CustomCarOrder order = customOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Custom order not found: " + orderId));
        return new Response(mapToDto(order));
    }

    private static CustomOrderDto mapToDto(CustomCarOrder order) {
        return new CustomOrderDto(
                order.getId().value(),
                order.getClientId().value(),
                order.getManagerId().value(),
                order.getConfiguration().getModel().getId().value(),
                new CustomOrderStatusDto(order.getStatus().name())
        );
    }
}
