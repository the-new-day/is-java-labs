package org.example.dealership.application.service.carcatalog;

import org.example.dealership.application.mapping.CarFilterMapper;
import org.example.dealership.application.mapping.CarMapper;
import org.example.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.example.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.domain.model.carfilter.CarFilter;

public class ListCarsInteractor implements ListCarsUseCase {
    private final CarRepository carRepository;

    public ListCarsInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarFilterDto dto = request.filter();
        CarFilter filter = CarFilterMapper.mapFromDto(dto);
        return new Response(
                carRepository.find(filter).stream()
                        .map(CarMapper::mapCarToSummaryDto)
                        .toList()
        );
    }
}
