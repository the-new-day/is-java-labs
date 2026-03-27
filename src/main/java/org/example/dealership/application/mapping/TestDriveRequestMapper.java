package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.id.TestDriveRequestId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.testdrive.TestDriveRequest;

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
