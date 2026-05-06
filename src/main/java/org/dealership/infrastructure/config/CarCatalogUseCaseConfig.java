package org.dealership.infrastructure.config;

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
    public GetCarUseCase getCarUseCase(CarRepository carRepository) {
        return new GetCarInteractor(carRepository);
    }

    @Bean
    public GetModelUseCase getModelUseCase(CarModelRepository carModelRepository) {
        return new GetModelInteractor(carModelRepository);
    }

    @Bean
    public ListCarsUseCase listCarsUseCase(CarRepository carRepository) {
        return new ListCarsInteractor(carRepository);
    }

    @Bean
    public ListModelsUseCase listModelsUseCase(CarModelRepository carModelRepository) {
        return new ListModelsInteractor(carModelRepository);
    }
}
