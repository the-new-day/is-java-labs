package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.user.User;
import org.dealership.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IdMapper.class)
public interface UserJpaMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "toUserId")
    User toDomain(UserJpaEntity entity);

    @Mapping(target = "id", source = "id", qualifiedByName = "toUuidFromUserId")
    UserJpaEntity toEntity(User user);
}
