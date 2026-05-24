package org.dealership.domain;

import org.dealership.domain.model.configuration.ComponentVariant;
import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.StockCarOrder;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void shouldUpdateStockOrderStatus() {
        StockCarOrder order = new StockCarOrder(
                new OrderId(UUID.randomUUID()),
                new UserId(UUID.randomUUID()),
                new UserId(UUID.randomUUID()),
                new CarId(UUID.randomUUID()),
                StockCarOrderStatus.PLACED
        );

        StockCarOrder updated = order.withStatus(StockCarOrderStatus.APPROVED_BY_MANAGER);

        assertEquals(StockCarOrderStatus.APPROVED_BY_MANAGER, updated.getStatus());
        assertEquals(order.getId(), updated.getId());
        assertEquals(order.getClientId(), updated.getClientId());
        assertEquals(order.getManagerId(), updated.getManagerId());
    }

    @Test
    void shouldUpdateCustomOrderStatus() {
        CarModelId modelId = new CarModelId(UUID.randomUUID());
        ComponentVariant wheels = DomainTestData.variant(ComponentType.WHEELS, modelId, BigDecimal.valueOf(100));
        ComponentVariantSelection selection = DomainTestData.selection(wheels);
        Configuration configuration = DomainTestData.configuration(
                DomainTestData.carModel(
                        modelId,
                        DomainTestData.brand(
                                DomainTestData.brandId("11111111-1111-1111-1111-111111111111")
                        ),
                        selection,
                        Set.of(ComponentType.WHEELS)
                ),
                selection
        );

        CustomCarOrder order = new CustomCarOrder(
                new OrderId(UUID.randomUUID()),
                new UserId(UUID.randomUUID()),
                new UserId(UUID.randomUUID()),
                configuration,
                CustomCarOrderStatus.PLACED
        );

        CustomCarOrder updated = order.withStatus(CustomCarOrderStatus.APPROVED_BY_WAREHOUSE);

        assertEquals(CustomCarOrderStatus.APPROVED_BY_WAREHOUSE, updated.getStatus());
        assertEquals(order.getId(), updated.getId());
    }
}
