package org.dealership;

import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.domain.model.id.UserId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@TestConfiguration
public class IntegrationTestConfig {

    public static final UUID SEED_MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");

    private static final UUID SEED_CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID SEED_WAREHOUSE_ID = UUID.fromString("00000000-0000-0000-0000-000000000303");
    private static final UUID SEED_ADMIN_ID = UUID.fromString("00000000-0000-0000-0000-000000000304");

    @Bean
    @Primary
    public ManagerProvider testManagerDirectory() {
        return () -> List.of(new UserId(SEED_MANAGER_ID));
    }

    @Bean
    @Primary
    public TestUserManager testUserIdentityProvider() {
        return new TestUserManager(Map.of(
                SEED_CLIENT_ID, "Ivan Petrov",
                SEED_MANAGER_ID, "Maria Manager",
                SEED_WAREHOUSE_ID, "Oleg Warehouse",
                SEED_ADMIN_ID, "Anna Admin"
        ));
    }

    @Bean
    @Primary
    public JwtDecoder testJwtDecoder() {
        return token -> {
            throw new UnsupportedOperationException(
                    """
                    JwtDecoder is not used in integration tests;
                    tests inject authentication via SecurityMockMvcRequestPostProcessors.jwt()
                    """
            );
        };
    }
}
