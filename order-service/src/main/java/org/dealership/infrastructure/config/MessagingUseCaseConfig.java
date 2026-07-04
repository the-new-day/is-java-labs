package org.dealership.infrastructure.config;

import org.dealership.application.port.in.messaging.ProcessOrderApprovalResponseUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.service.messaging.ProcessOrderApprovalResponseInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingUseCaseConfig {

    @Bean
    public ProcessOrderApprovalResponseUseCase processOrderApprovalResponseUseCase(
            StockCarOrderRepository stockOrderRepository,
            CustomCarOrderRepository customOrderRepository
    ) {
        return new ProcessOrderApprovalResponseInteractor(stockOrderRepository, customOrderRepository);
    }
}
