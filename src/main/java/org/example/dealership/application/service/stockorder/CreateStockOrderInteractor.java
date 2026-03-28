package org.example.dealership.application.service.stockorder;

import org.example.dealership.application.port.in.stockorder.CreateStockOrderUseCase;
import org.example.dealership.application.port.out.persistence.CarRepository;
import org.example.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.order.StockCarOrder;
import org.example.dealership.domain.model.order.state.StockCarOrderStatus;

public class CreateStockOrderInteractor implements CreateStockOrderUseCase {
    private final StockCarOrderRepository stockOrderRepository;
    private final CarRepository carRepository;

    public CreateStockOrderInteractor(
            StockCarOrderRepository stockOrderRepository,
            CarRepository carRepository
    ) {
        this.stockOrderRepository = stockOrderRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Response execute(Request request) {
        CarId carId = new CarId(request.carId());
        if (carRepository.findById(carId).isEmpty()) {
            throw new EntityNotFoundException("Car not found: " + carId);
        }
        StockCarOrder order = new StockCarOrder(
                stockOrderRepository.nextId(),
                new UserId(request.clientId()),
                new UserId(request.managerId()),
                carId,
                StockCarOrderStatus.PLACED
        );
        stockOrderRepository.save(order);
        return new Response(order.getId().value());
    }
}
