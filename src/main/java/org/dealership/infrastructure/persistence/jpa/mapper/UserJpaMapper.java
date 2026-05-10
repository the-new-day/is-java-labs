package org.dealership.infrastructure.persistence.jpa.mapper;

import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.user.User;
import org.dealership.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserJpaMapper {
    public User toDomain(UserJpaEntity entity) {
        return new User(new UserId(entity.getId()), entity.getFullName());
    }

    public UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(user.getId().value(), user.getFullName());
    }
}
