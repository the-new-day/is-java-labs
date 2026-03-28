package org.dealership.application.port.out.persistence;

import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.testdrive.TestDriveRequest;

import java.util.List;
import java.util.Optional;

public interface TestDriveRequestRepository {
    TestDriveRequestId nextId();
    void save(TestDriveRequest request);
    Optional<TestDriveRequest> findById(TestDriveRequestId id);
    List<TestDriveRequest> findByClientId(UserId clientId);
    List<TestDriveRequest> findByCarId(CarId carId);
    List<TestDriveRequest> findAll();
    void deleteById(TestDriveRequestId id);
}
