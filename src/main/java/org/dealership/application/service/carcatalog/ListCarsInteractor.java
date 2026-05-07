package org.dealership.application.service.carcatalog;

import org.dealership.application.mapper.CarFilterMapper;
import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.dealership.application.port.out.persistence.CarRepository;

public class ListCarsInteractor implements ListCarsUseCase {
    private final CarRepository carRepository;
    private final CarFilterMapper carFilterMapper;
    private final CarMapper carMapper;

    public ListCarsInteractor(CarRepository carRepository, CarFilterMapper carFilterMapper, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carFilterMapper = carFilterMapper;
        this.carMapper = carMapper;
    }

    @Override
    public Response execute(Request request) {
        CarFilterDto dto = request.filter();
        return new Response(
                carRepository.findByFilter(carFilterMapper.toDomain(dto)).stream()
                        .map(carMapper::toSummaryDto)
                        .toList()
        );
    }
}
