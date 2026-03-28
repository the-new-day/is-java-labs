package org.dealership.application.service.carcatalog;

import org.dealership.application.mapping.CarModelMapper;
import org.dealership.application.port.in.carcatalog.ListModelsUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;

public class ListModelsInteractor implements ListModelsUseCase {
    private final CarModelRepository carModelRepository;

    public ListModelsInteractor(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                carModelRepository.findAll().stream()
                        .map(CarModelMapper::mapToSummaryDto)
                        .toList()
        );
    }
}
