package org.dealership.infrastructure.persistence.jpa.adapter;

import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.testdrive.TestDriveRequest;
import org.dealership.infrastructure.persistence.jpa.entity.TestDriveRequestJpaEntity;
import org.dealership.infrastructure.persistence.jpa.mapper.TestDriveRequestJpaMapper;
import org.dealership.infrastructure.persistence.jpa.repository.TestDriveRequestJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TestDriveRequestJpaAdapter implements TestDriveRequestRepository {

    private final TestDriveRequestJpaRepository testDriveRequestJpaRepository;
    private final TestDriveRequestJpaMapper testDriveRequestJpaMapper;

    public TestDriveRequestJpaAdapter(
            TestDriveRequestJpaRepository testDriveRequestJpaRepository,
            TestDriveRequestJpaMapper testDriveRequestJpaMapper
    ) {
        this.testDriveRequestJpaRepository = testDriveRequestJpaRepository;
        this.testDriveRequestJpaMapper = testDriveRequestJpaMapper;
    }

    @Override
    public TestDriveRequestId nextId() {
        return new TestDriveRequestId(UUID.randomUUID());
    }

    @Override
    @Transactional
    public void save(TestDriveRequest request) {
        Optional<TestDriveRequestJpaEntity> existing =
                testDriveRequestJpaRepository.findByIdAndRemovedFalse(request.getId().value());
        if (existing.isPresent()) {
            TestDriveRequestJpaEntity entity = existing.get();
            entity.setClientId(request.getClientId().value());
            entity.setCarId(request.getCarId().value());
            entity.setStartsAt(request.getStartsAt());
            testDriveRequestJpaRepository.save(entity);
        } else {
            testDriveRequestJpaRepository.save(testDriveRequestJpaMapper.toEntity(request));
        }
    }

    @Override
    public Optional<TestDriveRequest> findById(TestDriveRequestId id) {
        return testDriveRequestJpaRepository.findByIdAndRemovedFalse(id.value())
                .map(testDriveRequestJpaMapper::toDomain);
    }

    @Override
    public List<TestDriveRequest> findByClientId(UserId clientId) {
        return testDriveRequestJpaRepository.findAllByClientIdAndRemovedFalse(clientId.value()).stream()
                .map(testDriveRequestJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<TestDriveRequest> findByCarId(CarId carId) {
        return testDriveRequestJpaRepository.findAllByCarIdAndRemovedFalse(carId.value()).stream()
                .map(testDriveRequestJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<TestDriveRequest> findAll() {
        return testDriveRequestJpaRepository.findAllByRemovedFalse().stream()
                .map(testDriveRequestJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(TestDriveRequestId id) {
        TestDriveRequestJpaEntity entity = testDriveRequestJpaRepository.findByIdAndRemovedFalse(id.value())
                .orElseThrow(() -> new EntityNotFoundException("TestDriveRequest not found: " + id));
        entity.markRemoved();
        testDriveRequestJpaRepository.save(entity);
    }
}
