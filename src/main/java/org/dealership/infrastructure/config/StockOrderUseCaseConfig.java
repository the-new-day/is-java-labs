package org.dealership.infrastructure.config;

import org.dealership.application.mapper.StockOrderMapper;
import org.dealership.application.mapper.StockOrderStatusMapper;
import org.dealership.application.port.in.stockorder.CreateStockOrderUseCase;
import org.dealership.application.port.in.stockorder.DeleteStockOrderUseCase;
import org.dealership.application.port.in.stockorder.GetStockOrderUseCase;
import org.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.dealership.application.port.in.stockorder.UpdateStockOrderUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.application.service.stockorder.CreateStockOrderInteractor;
import org.dealership.application.service.stockorder.DeleteStockOrderInteractor;
import org.dealership.application.service.stockorder.GetStockOrderInteractor;
import org.dealership.application.service.stockorder.ListStockOrdersInteractor;
import org.dealership.application.service.stockorder.UpdateStockOrderInteractor;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockOrderUseCaseConfig {

    @Bean
    public CreateStockOrderUseCase createStockOrderUseCase(
            StockCarOrderRepository stockCarOrderRepository,
            CarRepository carRepository,
            ManagerProvider managerProvider,
            UserSelectionStrategy userSelectionStrategy
    ) {
        return new CreateStockOrderInteractor(
                stockCarOrderRepository,
                carRepository,
                managerProvider,
                userSelectionStrategy);
    }

    @Bean
    public DeleteStockOrderUseCase deleteStockOrderUseCase(StockCarOrderRepository stockCarOrderRepository) {
        return new DeleteStockOrderInteractor(stockCarOrderRepository);
    }

    @Bean
    public GetStockOrderUseCase getStockOrderUseCase(StockCarOrderRepository stockCarOrderRepository, StockOrderMapper stockOrderMapper) {
        return new GetStockOrderInteractor(stockCarOrderRepository, stockOrderMapper);
    }

    @Bean
    public ListStockOrdersUseCase listStockOrdersUseCase(StockCarOrderRepository stockCarOrderRepository, StockOrderMapper stockOrderMapper) {
        return new ListStockOrdersInteractor(stockCarOrderRepository, stockOrderMapper);
    }

    @Bean
    public UpdateStockOrderUseCase updateStockOrderUseCase(StockCarOrderRepository stockCarOrderRepository, StockOrderStatusMapper stockOrderStatusMapper) {
        return new UpdateStockOrderInteractor(stockCarOrderRepository, stockOrderStatusMapper);
    }
}
