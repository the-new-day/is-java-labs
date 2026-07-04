package org.dealership.infrastructure.persistence.jpa.repository;

import org.dealership.infrastructure.persistence.jpa.entity.InboxMessageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InboxMessageJpaRepository extends JpaRepository<InboxMessageJpaEntity, UUID> {
}
