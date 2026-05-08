package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.domain.model.order.state.StockCarOrderStatus;
import org.dealership.infrastructure.persistence.jpa.entity.StockCarOrderJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.StockCarOrderJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class StockCarOrderRepositoryIT extends AbstractIntegrationTest {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000651");
    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private StockCarOrderJpaRepository stockCarOrderRepository;

    @Test
    void findByIdAndRemovedFalse_existingOrder_returnsOrder() {
        Optional<StockCarOrderJpaEntity> result = stockCarOrderRepository.findByIdAndRemovedFalse(ORDER_ID);

        assertThat(result).isPresent();
        StockCarOrderJpaEntity order = result.get();
        assertThat(order.getClientId()).isEqualTo(CLIENT_ID);
        assertThat(order.getManagerId()).isEqualTo(MANAGER_ID);
        assertThat(order.getCarId()).isEqualTo(CAR_ID);
        assertThat(order.getStatus()).isEqualTo(StockCarOrderStatus.PLACED);
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<StockCarOrderJpaEntity> result = stockCarOrderRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsSeedOrder() {
        List<StockCarOrderJpaEntity> orders = stockCarOrderRepository.findAllByRemovedFalse();

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void findAllByClientIdAndRemovedFalse_existingClient_returnsClientOrders() {
        List<StockCarOrderJpaEntity> orders = stockCarOrderRepository.findAllByClientIdAndRemovedFalse(CLIENT_ID);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void findAllByClientIdAndRemovedFalse_nonExistingClient_returnsEmpty() {
        List<StockCarOrderJpaEntity> orders = stockCarOrderRepository.findAllByClientIdAndRemovedFalse(UUID.randomUUID());

        assertThat(orders).isEmpty();
    }

    @Test
    void findAllByManagerIdAndRemovedFalse_existingManager_returnsManagerOrders() {
        List<StockCarOrderJpaEntity> orders = stockCarOrderRepository.findAllByManagerIdAndRemovedFalse(MANAGER_ID);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void findAllByManagerIdAndRemovedFalse_nonExistingManager_returnsEmpty() {
        List<StockCarOrderJpaEntity> orders = stockCarOrderRepository.findAllByManagerIdAndRemovedFalse(UUID.randomUUID());

        assertThat(orders).isEmpty();
    }
}
