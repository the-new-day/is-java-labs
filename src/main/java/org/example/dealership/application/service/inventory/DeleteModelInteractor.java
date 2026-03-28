package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.DeleteModelUseCase;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.CarModelId;

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
