package org.example.dealership.application.port.in.user.dto;

import java.util.UUID;

public record UserDto(
    UUID id,
    String fullName,
    UserRoleDto role
) {}
