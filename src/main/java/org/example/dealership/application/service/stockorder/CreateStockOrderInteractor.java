package org.example.dealership.application.service.stockorder;

import org.example.dealership.application.port.in.stockorder.CreateStockOrderUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.example.dealership.application.port.out.persistence.UserRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.order.UserSelectionStrategy;
import org.example.dealership.domain.model.order.StockCarOrder;
import org.example.dealership.domain.model.order.state.StockCarOrderStatus;
import org.example.dealership.domain.model.user.User;
import org.example.dealership.domain.model.user.UserRole;

import java.util.List;

public class CreateStockOrderInteractor implements CreateStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserSelectionStrategy userSelectionStrategy;

    public CreateStockOrderInteractor(
            StockCarOrderRepository stockOrderRepository,
            CarRepository carRepository,
            UserRepository userRepository,
            UserSelectionStrategy userSelectionStrategy
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.userSelectionStrategy = userSelectionStrategy;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        if (carRepository.findById(carId).isEmpty()) {
            throw new EntityNotFoundException("Car not found: " + carId);
        }

        List<UserId> managers = userRepository.findByRole(UserRole.MANAGER)
                .stream()
                .map(User::getId)
                .toList();

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
