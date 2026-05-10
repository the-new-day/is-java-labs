package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.port.in.common.dto.CarModelDto;
import org.dealership.application.port.in.inventory.UpdateModelUseCase;
import org.dealership.application.port.in.inventory.dto.NewModelDto;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarModelId;

import java.util.Optional;

public class UpdateModelInteractor implements UpdateModelUseCase {
    private final CarModelRepository carModelRepository;
    private final CarModelMapper carModelMapper;

    public UpdateModelInteractor(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
    }

    @Override
    public Response execute(Request request) {
        NewModelDto dto = request.model();
        CarModelId modelId = new CarModelId(request.modelId());
        Optional<CarModel> existing = carModelRepository.findById(modelId);
        if (existing.isEmpty()) {
            throw new EntityNotFoundException("Car model not found: " + modelId);
        }
        carModelRepository.save(carModelMapper.toDomain(dto, modelId, existing.get().getBrand()));
        return new Response();
    }
}
