package org.example.dealership.application.service.inventory;

import org.example.dealership.application.mapping.CarModelMapper;
import org.example.dealership.application.port.in.common.dto.CarModelDto;
import org.example.dealership.application.port.in.inventory.UpdateModelUseCase;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.CarModelId;

public class UpdateModelInteractor implements UpdateModelUseCase {
    private final CarModelRepository carModelRepository;

    public UpdateModelInteractor(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public Response execute(Request request) {
        CarModelDto dto = request.model();
        CarModelId modelId = new CarModelId(dto.id());
        if (carModelRepository.findById(modelId).isEmpty()) {
            throw new EntityNotFoundException("Car model not found: " + modelId);
        }
        carModelRepository.save(CarModelMapper.mapFromDto(dto));
        return new Response();
    }
}
