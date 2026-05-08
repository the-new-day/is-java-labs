package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.dealership.infrastructure.persistence.jpa.entity.CustomCarOrderJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.CustomCarOrderJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CustomCarOrderRepositoryIT extends AbstractIntegrationTest {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000652");
    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");
    private static final UUID CONFIG_ID = UUID.fromString("00000000-0000-0000-0000-000000000402");

    @Autowired
    private CustomCarOrderJpaRepository customCarOrderRepository;

    @Test
    void findByIdAndRemovedFalse_existingOrder_returnsOrderWithEagerAssociations() {
        Optional<CustomCarOrderJpaEntity> result = customCarOrderRepository.findByIdAndRemovedFalse(ORDER_ID);

        assertThat(result).isPresent();
        CustomCarOrderJpaEntity order = result.get();
        assertThat(order.getClientId()).isEqualTo(CLIENT_ID);
        assertThat(order.getManagerId()).isEqualTo(MANAGER_ID);
        assertThat(order.getStatus()).isEqualTo(CustomCarOrderStatus.PLACED);
        assertThat(order.getConfiguration()).isNotNull();
        assertThat(order.getConfiguration().getId()).isEqualTo(CONFIG_ID);
        assertThat(order.getConfiguration().getCarModel()).isNotNull();
        assertThat(order.getConfiguration().getCarModel().getBrand()).isNotNull();
        assertThat(order.getConfiguration().getCarModel().getBrand().getName()).isEqualTo("BMW");
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<CustomCarOrderJpaEntity> result = customCarOrderRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsSeedOrder() {
        List<CustomCarOrderJpaEntity> orders = customCarOrderRepository.findAllByRemovedFalse();

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void findAllByClientIdAndRemovedFalse_existingClient_returnsClientOrders() {
        List<CustomCarOrderJpaEntity> orders = customCarOrderRepository.findAllByClientIdAndRemovedFalse(CLIENT_ID);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void findAllByClientIdAndRemovedFalse_nonExistingClient_returnsEmpty() {
        List<CustomCarOrderJpaEntity> orders = customCarOrderRepository.findAllByClientIdAndRemovedFalse(UUID.randomUUID());

        assertThat(orders).isEmpty();
    }

    @Test
    void findAllByManagerIdAndRemovedFalse_existingManager_returnsManagerOrders() {
        List<CustomCarOrderJpaEntity> orders = customCarOrderRepository.findAllByManagerIdAndRemovedFalse(MANAGER_ID);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(ORDER_ID);
    }

    @Test
    void findAllByManagerIdAndRemovedFalse_nonExistingManager_returnsEmpty() {
        List<CustomCarOrderJpaEntity> orders = customCarOrderRepository.findAllByManagerIdAndRemovedFalse(UUID.randomUUID());

        assertThat(orders).isEmpty();
    }

    @Test
    void markRemoved_orderExcludedFromActiveFinders() {
        CustomCarOrderJpaEntity order = customCarOrderRepository.findByIdAndRemovedFalse(ORDER_ID).orElseThrow();
        order.markRemoved();
        customCarOrderRepository.saveAndFlush(order);

        Optional<CustomCarOrderJpaEntity> byId = customCarOrderRepository.findByIdAndRemovedFalse(ORDER_ID);
        List<CustomCarOrderJpaEntity> all = customCarOrderRepository.findAllByRemovedFalse();

        assertThat(byId).isEmpty();
        assertThat(all).isEmpty();
    }
}
