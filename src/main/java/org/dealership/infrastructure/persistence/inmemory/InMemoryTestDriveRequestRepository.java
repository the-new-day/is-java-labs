package org.dealership.infrastructure.persistence.inmemory;

import org.dealership.domain.model.testdrive.TestDriveRequest;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.id.UserId;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTestDriveRequestRepository extends InMemoryRepository<TestDriveRequestId, TestDriveRequest> implements TestDriveRequestRepository {
    @Override
    public TestDriveRequestId nextId() {
        return new TestDriveRequestId(UUID.randomUUID());
    }

    @Override
    protected TestDriveRequestId getId(TestDriveRequest entity) {
        return entity.getId();
    }

    @Override
    public void save(TestDriveRequest request) {
        super.save(request);
    }

    @Override
    public Optional<TestDriveRequest> findById(TestDriveRequestId id) {
        return super.findById(id);
    }

    @Override
    public List<TestDriveRequest> findByClientId(UserId clientId) {
        return storage.values().stream()
                .filter(request -> request.getClientId().equals(clientId))
                .toList();
    }

    @Override
    public List<TestDriveRequest> findByCarId(CarId carId) {
        return storage.values().stream()
                .filter(request -> request.getCarId().equals(carId))
                .toList();
    }

    @Override
    public List<TestDriveRequest> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(TestDriveRequestId id) {
        super.deleteById(id);
    }
}
