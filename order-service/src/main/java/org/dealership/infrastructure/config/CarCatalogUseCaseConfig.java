package org.dealership.infrastructure.config;

import org.dealership.application.mapper.CarFilterMapper;
import org.dealership.application.mapper.CarMapper;
import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.port.in.carcatalog.GetCarUseCase;
import org.dealership.application.port.in.carcatalog.GetModelUseCase;
import org.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.dealership.application.port.in.carcatalog.ListModelsUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.service.carcatalog.GetCarInteractor;
import org.dealership.application.service.carcatalog.GetModelInteractor;
import org.dealership.application.service.carcatalog.ListCarsInteractor;
import org.dealership.application.service.carcatalog.ListModelsInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarCatalogUseCaseConfig {

    @Bean
    public GetCarUseCase getCarUseCase(CarRepository carRepository, CarMapper carMapper) {
        return new GetCarInteractor(carRepository, carMapper);
    }

    @Bean
    public GetModelUseCase getModelUseCase(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        return new GetModelInteractor(carModelRepository, carModelMapper);
    }

    @Bean
    public ListCarsUseCase listCarsUseCase(
            CarRepository carRepository,
            CarFilterMapper carFilterMapper,
            CarMapper carMapper
    ) {
        return new ListCarsInteractor(carRepository, carFilterMapper, carMapper);
    }

    @Bean
    public ListModelsUseCase listModelsUseCase(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        return new ListModelsInteractor(carModelRepository, carModelMapper);
    }
}
