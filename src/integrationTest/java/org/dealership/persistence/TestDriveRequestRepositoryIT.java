package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.infrastructure.persistence.jpa.entity.TestDriveRequestJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.TestDriveRequestJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class TestDriveRequestRepositoryIT extends AbstractIntegrationTest {

    private static final UUID REQUEST_ID = UUID.fromString("00000000-0000-0000-0000-000000000653");
    private static final UUID CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    @Autowired
    private TestDriveRequestJpaRepository testDriveRequestRepository;

    @Test
    void findByIdAndRemovedFalse_existingRequest_returnsRequest() {
        Optional<TestDriveRequestJpaEntity> result = testDriveRequestRepository.findByIdAndRemovedFalse(REQUEST_ID);

        assertThat(result).isPresent();
        TestDriveRequestJpaEntity request = result.get();
        assertThat(request.getClientId()).isEqualTo(CLIENT_ID);
        assertThat(request.getCarId()).isEqualTo(CAR_ID);
        assertThat(request.getStartsAt()).isEqualTo(LocalDateTime.of(2026, 5, 6, 10, 0));
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<TestDriveRequestJpaEntity> result = testDriveRequestRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsSeedRequest() {
        List<TestDriveRequestJpaEntity> requests = testDriveRequestRepository.findAllByRemovedFalse();

        assertThat(requests).hasSize(1);
        assertThat(requests.get(0).getId()).isEqualTo(REQUEST_ID);
    }

    @Test
    void findAllByClientIdAndRemovedFalse_existingClient_returnsClientRequests() {
        List<TestDriveRequestJpaEntity> requests = testDriveRequestRepository.findAllByClientIdAndRemovedFalse(CLIENT_ID);

        assertThat(requests).hasSize(1);
        assertThat(requests.get(0).getId()).isEqualTo(REQUEST_ID);
    }

    @Test
    void findAllByClientIdAndRemovedFalse_nonExistingClient_returnsEmpty() {
        List<TestDriveRequestJpaEntity> requests = testDriveRequestRepository.findAllByClientIdAndRemovedFalse(UUID.randomUUID());

        assertThat(requests).isEmpty();
    }

    @Test
    void findAllByCarIdAndRemovedFalse_existingCar_returnsCarRequests() {
        List<TestDriveRequestJpaEntity> requests = testDriveRequestRepository.findAllByCarIdAndRemovedFalse(CAR_ID);

        assertThat(requests).hasSize(1);
        assertThat(requests.get(0).getId()).isEqualTo(REQUEST_ID);
    }

    @Test
    void findAllByCarIdAndRemovedFalse_nonExistingCar_returnsEmpty() {
        List<TestDriveRequestJpaEntity> requests = testDriveRequestRepository.findAllByCarIdAndRemovedFalse(UUID.randomUUID());

        assertThat(requests).isEmpty();
    }

    @Test
    void save_persistsNewRequest() {
        UUID newId = UUID.randomUUID();
        TestDriveRequestJpaEntity newRequest = new TestDriveRequestJpaEntity(
                newId, CLIENT_ID, CAR_ID, LocalDateTime.of(2026, 6, 1, 12, 0));

        testDriveRequestRepository.saveAndFlush(newRequest);

        Optional<TestDriveRequestJpaEntity> found = testDriveRequestRepository.findByIdAndRemovedFalse(newId);
        assertThat(found).isPresent();
        assertThat(found.get().getStartsAt()).isEqualTo(LocalDateTime.of(2026, 6, 1, 12, 0));
    }

    @Test
    void markRemoved_requestExcludedFromActiveFinders() {
        TestDriveRequestJpaEntity request = testDriveRequestRepository.findByIdAndRemovedFalse(REQUEST_ID).orElseThrow();
        request.markRemoved();
        testDriveRequestRepository.saveAndFlush(request);

        Optional<TestDriveRequestJpaEntity> byId = testDriveRequestRepository.findByIdAndRemovedFalse(REQUEST_ID);

        assertThat(byId).isEmpty();
    }
}
