package org.example.dealership.domain.model.order;

import org.example.dealership.domain.model.configuration.Configuration;
import org.example.dealership.domain.model.id.OrderId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.example.dealership.domain.validation.DomainChecks;

public class CustomCarOrder extends Order {
    private final Configuration configuration;
    private final CustomCarOrderStatus status;

    public CustomCarOrder(
            OrderId id,
            UserId clientId,
            UserId managerId,
            Configuration configuration,
            CustomCarOrderStatus status
    ) {
        super(id, clientId, managerId);
        this.configuration = DomainChecks.notNull(configuration, "configuration");
        this.status = DomainChecks.notNull(status, "status");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public CustomCarOrderStatus getStatus() {
        return status;
    }

    public CustomCarOrder withStatus(CustomCarOrderStatus newStatus) {
        return new CustomCarOrder(getId(), getClientId(), getManagerId(), configuration, newStatus);
    }
}
