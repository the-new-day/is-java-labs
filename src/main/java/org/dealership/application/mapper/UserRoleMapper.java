package org.dealership.application.mapper;

import org.dealership.application.port.in.user.dto.UserRoleDto;
import org.dealership.domain.model.user.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleDto toDto(UserRole role);
    UserRole toDomain(UserRoleDto dto);
}
