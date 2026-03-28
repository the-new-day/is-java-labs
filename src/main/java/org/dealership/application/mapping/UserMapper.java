package org.dealership.application.mapping;

import org.dealership.application.port.in.user.dto.UserDto;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;

public class UserMapper {
    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getId().value(),
                user.getFullName(),
                UserRoleMapper.mapToDto(user.getRole())
        );
    }

    public static User mapFromDto(UserDto dto) {
        return new User(
                new UserId(dto.id()),
                dto.fullName(),
                UserRoleMapper.mapFromDto(dto.role())
        );
    }
}
