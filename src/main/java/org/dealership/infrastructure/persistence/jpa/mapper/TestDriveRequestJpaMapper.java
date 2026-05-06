package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.testdrive.TestDriveRequest;
import org.dealership.infrastructure.persistence.jpa.entity.TestDriveRequestJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TestDriveRequestJpaMapper {
    public TestDriveRequest toDomain(TestDriveRequestJpaEntity entity) {
        return new TestDriveRequest(
                new TestDriveRequestId(entity.getId()),
                new UserId(entity.getClientId()),
                new CarId(entity.getCarId()),
                entity.getStartsAt()
        );
    }

    public TestDriveRequestJpaEntity toEntity(TestDriveRequest request) {
        return new TestDriveRequestJpaEntity(
                request.getId().value(),
                request.getClientId().value(),
                request.getCarId().value(),
                request.getStartsAt()
        );
    }
}
