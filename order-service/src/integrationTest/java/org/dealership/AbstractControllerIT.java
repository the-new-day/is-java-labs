package org.dealership;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest
@AutoConfigureMockMvc
@Import(IntegrationTestConfig.class)
@EmbeddedKafka(
        partitions = 1,
        topics = {"order.sent-for-approval", "order.approved", "order.rejected"}
)
public abstract class AbstractControllerIT {

    public static final UUID SEED_CLIENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    public static final UUID SEED_MANAGER_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");
    public static final UUID SEED_WAREHOUSE_ID = UUID.fromString("00000000-0000-0000-0000-000000000303");
    public static final UUID SEED_ADMIN_ID = UUID.fromString("00000000-0000-0000-0000-000000000304");

    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> "http://localhost/integration-test");
        registry.add("grpc.client.storage-service.address", () -> "static://localhost:19090");
        registry.add("grpc.client.storage-service.negotiation-type", () -> "plaintext");
    }

    protected static RequestPostProcessor jwtUser(UUID id, String... roles) {
        GrantedAuthority[] authorities = Arrays.stream(roles)
                .map(r -> "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .toArray(GrantedAuthority[]::new);
        return jwt()
                .jwt(b -> b
                        .subject(id.toString())
                        .claim("name", "Integration " + id)
                        .claim("preferred_username", "user-" + id))
                .authorities(authorities);
    }

    protected static RequestPostProcessor asAdmin() {
        return jwtUser(SEED_ADMIN_ID, "ADMIN");
    }

    protected static RequestPostProcessor asManager() {
        return jwtUser(SEED_MANAGER_ID, "MANAGER");
    }

    protected static RequestPostProcessor asClient() {
        return jwtUser(SEED_CLIENT_ID, "USER");
    }

    protected static RequestPostProcessor asWarehouseAdmin() {
        return jwtUser(SEED_WAREHOUSE_ID, "WAREHOUSE_ADMIN");
    }
}
