package org.dealership.application.service.inventory;

import org.dealership.application.mapping.CarMapper;
import org.dealership.application.port.in.inventory.ListCarsUseCase;
import org.dealership.application.port.out.persistence.CarRepository;

public class ListCarsInteractor implements ListCarsUseCase {
    private final CarRepository carRepository;

    public ListCarsInteractor(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                carRepository.findAll().stream()
                        .map(CarMapper::mapCarToInventorySummaryDto)
                        .toList()
        );
    }
}
