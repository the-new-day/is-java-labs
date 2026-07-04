package org.dealership;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@TestConfiguration
public class IntegrationTestConfig {

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
