package org.dealership.infrastructure.security;

import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.persistence.StockCarOrderRepository;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("orderAccess")
public class OrderAccess {

    private final CustomCarOrderRepository customOrderRepository;
    private final StockCarOrderRepository stockOrderRepository;
    private final TestDriveRequestRepository testDriveRepository;

    public OrderAccess(
            CustomCarOrderRepository customOrderRepository,
            StockCarOrderRepository stockOrderRepository,
            TestDriveRequestRepository testDriveRepository
    ) {
        this.customOrderRepository = customOrderRepository;
        this.stockOrderRepository = stockOrderRepository;
        this.testDriveRepository = testDriveRepository;
    }

    public boolean isCustomOrderOwner(UUID orderId, Authentication authentication) {
        UUID currentUser = currentUser(authentication);
        if (currentUser == null) {
            return false;
        }
        return customOrderRepository.findById(new OrderId(orderId))
                .map(o -> o.getClientId().value().equals(currentUser))
                .orElse(false);
    }

    public boolean isStockOrderOwner(UUID orderId, Authentication authentication) {
        UUID currentUser = currentUser(authentication);
        if (currentUser == null) {
            return false;
        }
        return stockOrderRepository.findById(new OrderId(orderId))
                .map(o -> o.getClientId().value().equals(currentUser))
                .orElse(false);
    }

    public boolean isTestDriveOwner(UUID requestId, Authentication authentication) {
        UUID currentUser = currentUser(authentication);
        if (currentUser == null) {
            return false;
        }
        return testDriveRepository.findById(new TestDriveRequestId(requestId))
                .map(r -> r.getClientId().value().equals(currentUser))
                .orElse(false);
    }

    public boolean isSelf(UUID userId, Authentication authentication) {
        UUID currentUser = currentUser(authentication);
        return currentUser != null && currentUser.equals(userId);
    }

    private static UUID currentUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String sub = jwt.getSubject();
            if (sub == null) {
                return null;
            }
            try {
                return UUID.fromString(sub);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
        return null;
    }
}
