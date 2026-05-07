package org.dealership.infrastructure.config;

import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.port.in.customorder.CreateCustomOrderUseCase;
import org.dealership.application.port.in.customorder.DeleteCustomOrderUseCase;
import org.dealership.application.port.in.customorder.GetCustomOrderUseCase;
import org.dealership.application.port.in.customorder.ListCustomOrdersUseCase;
import org.dealership.application.port.in.customorder.UpdateCustomOrderUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.application.service.customorder.CreateCustomOrderInteractor;
import org.dealership.application.service.customorder.DeleteCustomOrderInteractor;
import org.dealership.application.service.customorder.GetCustomOrderInteractor;
import org.dealership.application.service.customorder.ListCustomOrdersInteractor;
import org.dealership.application.service.customorder.UpdateCustomOrderInteractor;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomOrderUseCaseConfig {

    @Bean
    public CreateCustomOrderUseCase createCustomOrderUseCase(
            CustomCarOrderRepository customCarOrderRepository,
            CarModelRepository carModelRepository,
            UserRepository userRepository,
            UserSelectionStrategy userSelectionStrategy,
            ConfigurationMapper configurationMapper
    ) {
        return new CreateCustomOrderInteractor(
                customCarOrderRepository,
                carModelRepository,
                userRepository,
                userSelectionStrategy,
                configurationMapper);
    }

    @Bean
    public DeleteCustomOrderUseCase deleteCustomOrderUseCase(CustomCarOrderRepository customCarOrderRepository) {
        return new DeleteCustomOrderInteractor(customCarOrderRepository);
    }

    @Bean
    public GetCustomOrderUseCase getCustomOrderUseCase(CustomCarOrderRepository customCarOrderRepository) {
        return new GetCustomOrderInteractor(customCarOrderRepository);
    }

    @Bean
    public ListCustomOrdersUseCase listCustomOrdersUseCase(CustomCarOrderRepository customCarOrderRepository) {
        return new ListCustomOrdersInteractor(customCarOrderRepository);
    }

    @Bean
    public UpdateCustomOrderUseCase updateCustomOrderUseCase(CustomCarOrderRepository customCarOrderRepository) {
        return new UpdateCustomOrderInteractor(customCarOrderRepository);
    }
}
