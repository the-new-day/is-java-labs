package org.dealership.migration;

import org.dealership.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LiquibaseMigrationIT extends AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void allExpectedTablesExist() {
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'",
                String.class
        );
        assertThat(tables).contains(
                "brands",
                "car_model_base_component_variants",
                "car_model_configurable_component_types",
                "car_models",
                "cars",
                "component_variant_compatible_models",
                "component_variants",
                "configuration_component_variants",
                "configurations",
                "custom_car_orders",
                "spare_part_compatible_models",
                "spare_parts",
                "stock_car_orders",
                "test_drive_requests",
                "users"
        );
    }

    @Test
    void brandsTableHasExpectedColumns() {
        List<String> columns = jdbcTemplate.queryForList(
                """
                    SELECT column_name FROM information_schema.columns
                    WHERE table_schema = 'public' AND table_name = 'brands'
                    ORDER BY column_name
                    """,
                String.class
        );
        assertThat(columns).containsExactlyInAnyOrder("id", "created_at", "updated_at", "removed", "name");
    }

    @Test
    void seedDataBrandIsPresent() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM brands WHERE removed = false",
                Integer.class
        );
        assertThat(count).isEqualTo(1);
    }

    @Test
    void seedDataCarModelsArePresent() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM car_models WHERE removed = false",
                Integer.class
        );
        assertThat(count).isEqualTo(3);
    }

    @Test
    void seedDataUsersArePresent() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE removed = false",
                Integer.class
        );
        assertThat(count).isEqualTo(4);
    }

    @Test
    void seedDataCarIsPresent() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cars WHERE removed = false",
                Integer.class
        );
        assertThat(count).isEqualTo(1);
    }

    @Test
    void seedDataStockOrderIsPresent() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM stock_car_orders WHERE removed = false",
                Integer.class
        );
        assertThat(count).isEqualTo(1);
    }

    @Test
    void foreignKeyFromCarModelsToBrandsIsEnforced() {
        Integer violatingRowCount = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*) FROM car_models cm
                LEFT JOIN brands b ON cm.brand_id = b.id
                WHERE b.id IS NULL
                """,
                Integer.class
        );
        assertThat(violatingRowCount).isZero();
    }
}
