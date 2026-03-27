package org.example.dealership.application.mapping;

import org.example.dealership.application.port.in.user.dto.UserRoleDto;
import org.example.dealership.domain.exception.DomainValidationException;
import org.example.dealership.domain.model.user.UserRole;

public class UserRoleMapper {
    public static UserRoleDto mapToDto(UserRole role) {
        return new UserRoleDto(role.name());
    }

    public static UserRole mapFromDto(UserRoleDto dto) {
        try {
            return UserRole.valueOf(dto.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainValidationException("Unsupported user role: " + dto.name());
        }
    }
}
