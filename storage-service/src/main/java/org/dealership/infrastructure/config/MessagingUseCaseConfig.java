package org.dealership.infrastructure.config;

import org.dealership.application.port.in.messaging.ProcessOrderApprovalRequestUseCase;
import org.dealership.application.port.out.messaging.OrderResponseEventPort;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.service.messaging.ProcessOrderApprovalRequestInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingUseCaseConfig {

    @Bean
    public ProcessOrderApprovalRequestUseCase processOrderApprovalRequestUseCase(
            AssemblyOrderRepository assemblyOrderRepository,
            CarRepository carRepository,
            CarModelRepository carModelRepository,
            OrderResponseEventPort orderResponseEventPort
    ) {
        return new ProcessOrderApprovalRequestInteractor(
                assemblyOrderRepository,
                carRepository,
                carModelRepository,
                orderResponseEventPort
        );
    }
}
