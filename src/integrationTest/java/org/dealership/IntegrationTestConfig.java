package org.dealership;

import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.domain.model.id.UserId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.List;
import java.util.UUID;

@TestConfiguration
public class IntegrationTestConfig {

    public static final UUID SEED_MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");

    @Bean
    @Primary
    public ManagerProvider testManagerDirectory() {
        return () -> List.of(new UserId(SEED_MANAGER_ID));
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
