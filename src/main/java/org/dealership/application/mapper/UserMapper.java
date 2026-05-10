package org.dealership.application.mapper;

import org.dealership.application.port.in.user.dto.UserDto;
import org.dealership.domain.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseIdMapper.class})
public interface UserMapper {

    @Mapping(target = "id", source = "id.value")
    UserDto toDto(User user);

    @Mapping(target = "id", source = "id", qualifiedByName = "toUserId")
    User toDomain(UserDto dto);
}
