package org.dealership.domain.model.testdrive;

import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.validation.DomainChecks;

import java.time.LocalDateTime;

public class TestDriveRequest {
    private final TestDriveRequestId id;
    private final UserId clientId;
    private final CarId carId;
    private final LocalDateTime startsAt;

    public TestDriveRequest(TestDriveRequestId id, UserId clientId, CarId carId, LocalDateTime startsAt) {
        this.id = DomainChecks.notNull(id, "requestId");
        this.clientId = DomainChecks.notNull(clientId, "clientId");
        this.carId = DomainChecks.notNull(carId, "carId");
        this.startsAt = DomainChecks.notNull(startsAt, "startsAt");
    }

    public TestDriveRequestId getId() {
        return id;
    }

    public UserId getClientId() {
        return clientId;
    }

    public CarId getCarId() {
        return carId;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }
}
