package org.dealership.application.service.inventory;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.port.in.inventory.ListCarsUseCase;
import org.dealership.application.port.out.persistence.CarRepository;

public class ListCarsInteractor implements ListCarsUseCase {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public ListCarsInteractor(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                carRepository.findAll().stream()
                        .map(carMapper::toInventorySummaryDto)
                        .toList()
        );
    }
}
