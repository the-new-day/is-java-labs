package org.example.dealership.domain.repository;

import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.TestDriveRequestId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.testdrive.TestDriveRequest;

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
