package org.dealership.application.mapper;

import org.dealership.application.port.in.testdrive.dto.NewTestDriveRequestDto;
import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.testdrive.TestDriveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class})
public interface TestDriveRequestMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "clientId", source = "clientId.value")
    @Mapping(target = "carId", source = "carId.value")
    TestDriveRequestDto toDto(TestDriveRequest request);

    @Mapping(target = "id", source = "id", qualifiedByName = "toTestDriveRequestId")
    @Mapping(target = "clientId", source = "clientId", qualifiedByName = "toUserId")
    @Mapping(target = "carId", source = "carId", qualifiedByName = "toCarId")
    TestDriveRequest toDomain(TestDriveRequestDto dto);

    @Mapping(target = "id", source = "requestId")
    @Mapping(target = "clientId", source = "dto.clientId", qualifiedByName = "toUserId")
    @Mapping(target = "carId", source = "dto.carId", qualifiedByName = "toCarId")
    TestDriveRequest toDomain(TestDriveRequestId requestId, NewTestDriveRequestDto dto);
}
