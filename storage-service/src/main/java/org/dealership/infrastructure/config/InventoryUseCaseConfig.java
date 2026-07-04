package org.dealership.infrastructure.config;

import org.dealership.application.mapper.CarMapper;
import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.mapper.ColorMapper;
import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.*;
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
import org.dealership.application.service.inventory.GetInventoryCarInteractor;
import org.dealership.application.service.inventory.GetSparePartInteractor;
import org.dealership.application.service.inventory.ListInventoryCarsInteractor;
import org.dealership.application.service.inventory.ListSparePartsInteractor;
import org.dealership.application.service.inventory.UpdateCarInteractor;
import org.dealership.application.service.inventory.UpdateModelInteractor;
import org.dealership.application.service.inventory.UpdateSparePartInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryUseCaseConfig {

    @Bean
    public AddCarUseCase addCarUseCase(CarRepository carRepository, CarModelRepository carModelRepository, CarMapper carMapper) {
        return new AddCarInteractor(carRepository, carModelRepository, carMapper);
    }

    @Bean
    public AddModelUseCase addModelUseCase(CarModelRepository carModelRepository, BrandRepository brandRepository, CarModelMapper carModelMapper) {
        return new AddModelInteractor(carModelRepository, brandRepository, carModelMapper);
    }

    @Bean
    public AddSparePartUseCase addSparePartUseCase(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        return new AddSparePartInteractor(sparePartRepository, sparePartMapper);
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
    public GetInventoryCarUseCase getInventoryCarUseCase(CarRepository carRepository, CarMapper carMapper) {
        return new GetInventoryCarInteractor(carRepository, carMapper);
    }

    @Bean
    public GetSparePartUseCase getSparePartUseCase(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        return new GetSparePartInteractor(sparePartRepository, sparePartMapper);
    }

    @Bean
    public ListInventoryCarsUseCase listInventoryCarsUseCase(CarRepository carRepository, CarMapper carMapper) {
        return new ListInventoryCarsInteractor(carRepository, carMapper);
    }

    @Bean
    public ListSparePartsUseCase listSparePartsUseCase(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        return new ListSparePartsInteractor(sparePartRepository, sparePartMapper);
    }

    @Bean
    public UpdateCarUseCase updateCarUseCase(
            CarRepository carRepository,
            CarModelRepository carModelRepository,
            ConfigurationMapper configurationMapper,
            ColorMapper colorMapper
    ) {
        return new UpdateCarInteractor(carRepository, carModelRepository, configurationMapper, colorMapper);
    }

    @Bean
    public UpdateModelUseCase updateModelUseCase(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        return new UpdateModelInteractor(carModelRepository, carModelMapper);
    }

    @Bean
    public UpdateSparePartUseCase updateSparePartUseCase(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        return new UpdateSparePartInteractor(sparePartRepository, sparePartMapper);
    }
}
