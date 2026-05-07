package org.dealership.application.service.carcatalog;

import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.port.in.carcatalog.ListModelsUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;

public class ListModelsInteractor implements ListModelsUseCase {
    private final CarModelRepository carModelRepository;
    private final CarModelMapper carModelMapper;

    public ListModelsInteractor(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                carModelRepository.findAll().stream()
                        .map(carModelMapper::toSummaryDto)
                        .toList()
        );
    }
}
