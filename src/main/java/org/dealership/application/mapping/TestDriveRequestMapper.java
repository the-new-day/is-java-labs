package org.dealership.application.mapping;

import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.testdrive.TestDriveRequest;

public class TestDriveRequestMapper {
    public static TestDriveRequestDto mapToDto(TestDriveRequest request) {
        return new TestDriveRequestDto(
                request.getId().value(),
                request.getClientId().value(),
                request.getCarId().value(),
                request.getStartsAt()
        );
    }

    public static TestDriveRequest mapFromDto(TestDriveRequestDto dto) {
        return new TestDriveRequest(
                new TestDriveRequestId(dto.id()),
                new UserId(dto.clientId()),
                new CarId(dto.carId()),
                dto.startsAt()
        );
    }
}
