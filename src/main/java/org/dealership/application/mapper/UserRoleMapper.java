package org.dealership.application.mapper;

import org.dealership.application.port.in.user.dto.UserRoleDto;
import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.model.user.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserRoleDto toDto(UserRole role);

    default UserRole toDomain(UserRoleDto dto) {
        try {
            return UserRole.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported user role: " + dto.name());
        }
    }
}
