package org.dealership.domain.model.order;

import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.dealership.domain.validation.DomainChecks;

public class StockCarOrder extends Order {
    private final CarId carId;
    private final StockCarOrderStatus status;

    public StockCarOrder(
            OrderId id,
            UserId clientId,
            UserId managerId,
            CarId carId,
            StockCarOrderStatus status
    ) {
        super(id, clientId, managerId);
        this.carId = DomainChecks.notNull(carId, "carId");
        this.status = DomainChecks.notNull(status, "status");
    }

    public CarId getCarId() {
        return carId;
    }

    public StockCarOrderStatus getStatus() {
        return status;
    }

    public StockCarOrder withStatus(StockCarOrderStatus newStatus) {
        return new StockCarOrder(getId(), getClientId(), getManagerId(), carId, newStatus);
    }
}
