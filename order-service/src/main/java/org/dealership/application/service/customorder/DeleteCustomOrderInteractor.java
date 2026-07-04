package org.dealership.application.service.customorder;

import org.dealership.application.port.in.customorder.DeleteCustomOrderUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.OrderId;

public class DeleteCustomOrderInteractor implements DeleteCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;

    public DeleteCustomOrderInteractor(CustomCarOrderRepository customOrderRepository) {
        this.customOrderRepository = customOrderRepository;
    }

    @Override
    public Response execute(Request request) {
        OrderId orderId = new OrderId(request.id());
        if (customOrderRepository.findById(orderId).isEmpty()) {
            throw new EntityNotFoundException("Custom order not found: " + orderId);
        }
        customOrderRepository.deleteById(orderId);
        return new Response();
    }
}
