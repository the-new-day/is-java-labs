package org.dealership.infrastructure.config;

import org.dealership.application.port.in.inventory.AddCarUseCase;
import org.dealership.application.port.in.inventory.AddModelUseCase;
import org.dealership.application.port.in.inventory.AddSparePartUseCase;
import org.dealership.application.port.in.inventory.DeleteCarUseCase;
import org.dealership.application.port.in.inventory.DeleteModelUseCase;
import org.dealership.application.port.in.inventory.DeleteSparePartUseCase;
import org.dealership.application.port.in.inventory.GetCarUseCase;
import org.dealership.application.port.in.inventory.GetSparePartUseCase;
import org.dealership.application.port.in.inventory.ListCarsUseCase;
import org.dealership.application.port.in.inventory.ListSparePartsUseCase;
import org.dealership.application.port.in.inventory.UpdateCarUseCase;
import org.dealership.application.port.in.inventory.UpdateModelUseCase;
import org.dealership.application.port.in.inventory.UpdateSparePartUseCase;
import org.dealership.application.port.out.persistence.BrandRepository;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.application.service.inventory.AddCarInteractor;
import org.dealership.application.service.inventory.AddModelInteractor;
import org.dealership.application.service.inventory.AddSparePartInteractor;
import org.dealership.application.service.inventory.DeleteCarInteractor;
import org.dealership.application.service.inventory.DeleteModelInteractor;
import org.dealership.application.service.inventory.DeleteSparePartInteractor;
import org.dealership.application.service.inventory.GetCarInteractor;
import org.dealership.application.service.inventory.GetSparePartInteractor;
import org.dealership.application.service.inventory.ListCarsInteractor;
import org.dealership.application.service.inventory.ListSparePartsInteractor;
import org.dealership.application.service.inventory.UpdateCarInteractor;
import org.dealership.application.service.inventory.UpdateModelInteractor;
import org.dealership.application.service.inventory.UpdateSparePartInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryUseCaseConfig {

    @Bean
    public AddCarUseCase addCarUseCase(CarRepository carRepository, CarModelRepository carModelRepository) {
        return new AddCarInteractor(carRepository, carModelRepository);
    }

    @Bean
    public AddModelUseCase addModelUseCase(CarModelRepository carModelRepository, BrandRepository brandRepository) {
        return new AddModelInteractor(carModelRepository, brandRepository);
    }

    @Bean
    public AddSparePartUseCase addSparePartUseCase(SparePartRepository sparePartRepository) {
        return new AddSparePartInteractor(sparePartRepository);
    }

    @Bean
    public DeleteCarUseCase deleteCarUseCase(CarRepository carRepository) {
        return new DeleteCarInteractor(carRepository);
    }

    @Bean
    public DeleteModelUseCase deleteModelUseCase(CarModelRepository carModelRepository) {
        return new DeleteModelInteractor(carModelRepository);
    }

    @Bean
    public DeleteSparePartUseCase deleteSparePartUseCase(SparePartRepository sparePartRepository) {
        return new DeleteSparePartInteractor(sparePartRepository);
    }

    @Bean
    public GetCarUseCase getCarUseCase(CarRepository carRepository) {
        return new GetCarInteractor(carRepository);
    }

    @Bean
    public GetSparePartUseCase getSparePartUseCase(SparePartRepository sparePartRepository) {
        return new GetSparePartInteractor(sparePartRepository);
    }

    @Bean
    public ListCarsUseCase listCarsUseCase(CarRepository carRepository) {
        return new ListCarsInteractor(carRepository);
    }

    @Bean
    public ListSparePartsUseCase listSparePartsUseCase(SparePartRepository sparePartRepository) {
        return new ListSparePartsInteractor(sparePartRepository);
    }

    @Bean
    public UpdateCarUseCase updateCarUseCase(CarRepository carRepository, CarModelRepository carModelRepository) {
        return new UpdateCarInteractor(carRepository, carModelRepository);
    }

    @Bean
    public UpdateModelUseCase updateModelUseCase(CarModelRepository carModelRepository) {
        return new UpdateModelInteractor(carModelRepository);
    }

    @Bean
    public UpdateSparePartUseCase updateSparePartUseCase(SparePartRepository sparePartRepository) {
        return new UpdateSparePartInteractor(sparePartRepository);
    }
}
