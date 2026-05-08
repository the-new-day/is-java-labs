package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.infrastructure.persistence.jpa.entity.BrandJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.BrandJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BrandRepositoryIT extends AbstractIntegrationTest {

    private static final UUID BMW_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    private BrandJpaRepository brandRepository;

    @Test
    void findByIdAndRemovedFalse_existingBrand_returnsBrand() {
        Optional<BrandJpaEntity> result = brandRepository.findByIdAndRemovedFalse(BMW_ID);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("BMW");
        assertThat(result.get().isRemoved()).isFalse();
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<BrandJpaEntity> result = brandRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsSeedBrands() {
        List<BrandJpaEntity> brands = brandRepository.findAllByRemovedFalse();

        assertThat(brands).hasSize(1);
        assertThat(brands.getFirst().getName()).isEqualTo("BMW");
    }

    @Test
    void findByNameAndRemovedFalse_existingName_returnsBrand() {
        Optional<BrandJpaEntity> result = brandRepository.findByNameAndRemovedFalse("BMW");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(BMW_ID);
    }

    @Test
    void findByNameAndRemovedFalse_nonExistingName_returnsEmpty() {
        Optional<BrandJpaEntity> result = brandRepository.findByNameAndRemovedFalse("NonExistentBrand");

        assertThat(result).isEmpty();
    }

    @Test
    void save_persistsNewBrand() {
        UUID newId = UUID.randomUUID();
        BrandJpaEntity newBrand = new BrandJpaEntity(newId, "Audi");

        brandRepository.saveAndFlush(newBrand);

        Optional<BrandJpaEntity> found = brandRepository.findByIdAndRemovedFalse(newId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Audi");
    }

    @Test
    void markRemoved_brandNoLongerReturnedByActiveFinder() {
        BrandJpaEntity brand = brandRepository.findByIdAndRemovedFalse(BMW_ID).orElseThrow();
        brand.markRemoved();
        brandRepository.saveAndFlush(brand);

        Optional<BrandJpaEntity> result = brandRepository.findByIdAndRemovedFalse(BMW_ID);

        assertThat(result).isEmpty();
    }
}
