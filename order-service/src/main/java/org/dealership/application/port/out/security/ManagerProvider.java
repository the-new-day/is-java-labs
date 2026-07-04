package org.dealership.application.port.out.security;

import org.dealership.domain.model.id.UserId;

import java.util.List;

public interface ManagerProvider {
    List<UserId> listManagerIds();
}
