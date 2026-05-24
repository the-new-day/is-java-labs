package org.dealership.infrastructure.config;

import org.dealership.application.port.in.inventory.GetAvailableCarUseCase;
import org.dealership.application.port.in.inventory.ListAvailableCarsUseCase;
import org.dealership.application.port.out.inventory.AvailableCarsPort;
import org.dealership.application.service.inventory.GetAvailableCarInteractor;
import org.dealership.application.service.inventory.ListAvailableCarsInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvailableCarsUseCaseConfig {

    @Bean
    public ListAvailableCarsUseCase listAvailableCarsUseCase(AvailableCarsPort availableCarsPort) {
        return new ListAvailableCarsInteractor(availableCarsPort);
    }

    @Bean
    public GetAvailableCarUseCase getAvailableCarUseCase(AvailableCarsPort availableCarsPort) {
        return new GetAvailableCarInteractor(availableCarsPort);
    }
}
