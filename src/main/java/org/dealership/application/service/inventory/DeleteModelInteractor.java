package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.DeleteModelUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarModelId;

public class DeleteModelInteractor implements DeleteModelUseCase {
    private final CarModelRepository carModelRepository;

    public DeleteModelInteractor(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public Response execute(Request request) {
        CarModelId modelId = new CarModelId(request.id());
        if (carModelRepository.findById(modelId).isEmpty()) {
            throw new EntityNotFoundException("Car model not found: " + modelId);
        }
        carModelRepository.deleteById(modelId);
        return new Response();
    }
}
