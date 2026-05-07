package org.dealership.application.service.carcatalog;

import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.port.in.carcatalog.GetModelUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.CarModelId;

public class GetModelInteractor implements GetModelUseCase {
    private final CarModelRepository carModelRepository;
    private final CarModelMapper carModelMapper;

    public GetModelInteractor(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
    }

    @Override
    public Response execute(Request request) {
        CarModelId carModelId = new CarModelId(request.modelId());
        CarModel model = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new EntityNotFoundException("Model not found: " + carModelId));
        return new Response(carModelMapper.toDto(model));
    }
}
