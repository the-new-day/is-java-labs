package org.dealership.application.service.carcatalog;

import org.dealership.application.mapping.CarFilterMapper;
import org.dealership.application.mapping.CarMapper;
import org.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.model.carfilter.CarFilter;

public class ListCarsInteractor implements ListCarsUseCase {
    private final CarRepository carRepository;

    public ListCarsInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarFilterDto dto = request.filter();
        return new Response(
                carRepository.findBySpec(CarFilterMapper.mapSpecFromDto(dto)).stream()
                        .map(CarMapper::mapCarToSummaryDto)
                        .toList()
        );
    }
}
