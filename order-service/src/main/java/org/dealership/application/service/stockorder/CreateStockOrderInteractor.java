package org.dealership.application.service.stockorder;

import org.dealership.application.port.in.stockorder.CreateStockOrderUseCase;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.dealership.domain.model.order.StockCarOrder;
import org.dealership.domain.model.order.state.StockCarOrderStatus;

import java.util.List;

public class CreateStockOrderInteractor implements CreateStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final CarRepository carRepository;
    private final ManagerProvider managerProvider;
    private final UserSelectionStrategy userSelectionStrategy;

    public CreateStockOrderInteractor(
            StockCarOrderRepository stockOrderRepository,
            CarRepository carRepository,
            ManagerProvider managerProvider,
            UserSelectionStrategy userSelectionStrategy
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.carRepository = carRepository;
        this.managerProvider = managerProvider;
        this.userSelectionStrategy = userSelectionStrategy;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        if (carRepository.findById(carId).isEmpty()) {
            throw new EntityNotFoundException("Car not found: " + carId);
        }

        List<UserId> managers = managerProvider.listManagerIds();

        StockCarOrder order = new StockCarOrder(
                stockOrderRepository.nextId(),
                new UserId(request.clientId()),
                userSelectionStrategy.selectUser(managers),
                carId,
                StockCarOrderStatus.PLACED
        );
        stockOrderRepository.save(order);
        return new Response(order.getId().value());
    }
}
