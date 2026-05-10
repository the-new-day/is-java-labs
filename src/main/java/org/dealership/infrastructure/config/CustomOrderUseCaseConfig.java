package org.dealership.infrastructure.config;

import org.dealership.application.mapper.BaseIdMapper;
import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.mapper.CustomOrderMapper;
import org.dealership.application.mapper.CustomOrderStatusMapper;
import org.dealership.application.port.in.customorder.*;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.application.service.customorder.*;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomOrderUseCaseConfig {

    @Bean
    public CreateCustomOrderUseCase createCustomOrderUseCase(
            CustomCarOrderRepository customCarOrderRepository,
            CarModelRepository carModelRepository,
            ManagerProvider managerProvider,
            UserSelectionStrategy userSelectionStrategy,
            ConfigurationMapper configurationMapper
    ) {
        return new CreateCustomOrderInteractor(
                customCarOrderRepository,
                carModelRepository,
                managerProvider,
                userSelectionStrategy,
                configurationMapper);
    }

    @Bean
    public DeleteCustomOrderUseCase deleteCustomOrderUseCase(CustomCarOrderRepository customCarOrderRepository) {
        return new DeleteCustomOrderInteractor(customCarOrderRepository);
    }

    @Bean
    public GetCustomOrderUseCase getCustomOrderUseCase(
            CustomCarOrderRepository customCarOrderRepository,
            CustomOrderMapper customOrderMapper
    ) {
        return new GetCustomOrderInteractor(customCarOrderRepository, customOrderMapper);
    }

    @Bean
    public ListCustomOrdersUseCase listCustomOrdersUseCase(
            CustomCarOrderRepository customCarOrderRepository,
            CustomOrderMapper customOrderMapper
    ) {
        return new ListCustomOrdersInteractor(customCarOrderRepository, customOrderMapper);
    }

    @Bean
    public ListClientCustomOrdersUseCase listClientCustomOrdersUseCase(
            CustomCarOrderRepository customCarOrderRepository,
            BaseIdMapper idMapper,
            CustomOrderMapper customOrderMapper
    ) {
        return new ListClientCustomOrdersInteractor(
                customCarOrderRepository, idMapper, customOrderMapper
        );
    }

    @Bean
    public UpdateCustomOrderUseCase updateCustomOrderUseCase(
            CustomCarOrderRepository customCarOrderRepository,
            CustomOrderStatusMapper statusMapper
    ) {
        return new UpdateCustomOrderInteractor(customCarOrderRepository, statusMapper);
    }
}
