package org.dealership.application.service.customorder;

import org.dealership.application.port.in.customorder.ListCustomOrdersUseCase;
import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.dealership.application.port.in.customorder.dto.CustomOrderStatusDto;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.model.order.CustomCarOrder;

public class ListCustomOrdersInteractor implements ListCustomOrdersUseCase {
    private final CustomCarOrderRepository customOrderRepository;

    public ListCustomOrdersInteractor(CustomCarOrderRepository customOrderRepository) {
        this.customOrderRepository = customOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                customOrderRepository.findAll().stream()
                        .map(ListCustomOrdersInteractor::mapToDto)
                        .toList()
        );
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
