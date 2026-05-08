package org.dealership.persistence;

import org.dealership.AbstractIntegrationTest;
import org.dealership.infrastructure.persistence.jpa.entity.CarJpaEntity;
import org.dealership.infrastructure.persistence.jpa.repository.CarJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CarRepositoryIT extends AbstractIntegrationTest {

    private static final UUID CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");
    private static final String CAR_VIN = "WBA8E9G50JNU12345";

    @Autowired
    private CarJpaRepository carRepository;

    @Test
    void findByIdAndRemovedFalse_existingCar_returnsCarWithEagerAssociations() {
        Optional<CarJpaEntity> result = carRepository.findByIdAndRemovedFalse(CAR_ID);

        assertThat(result).isPresent();
        CarJpaEntity car = result.get();
        assertThat(car.getVin()).isEqualTo(CAR_VIN);
        assertThat(car.getConfiguration()).isNotNull();
        assertThat(car.getConfiguration().getCarModel()).isNotNull();
        assertThat(car.getConfiguration().getCarModel().getBrand()).isNotNull();
        assertThat(car.getConfiguration().getCarModel().getBrand().getName()).isEqualTo("BMW");
        assertThat(car.getConfiguration().getCarModel().getModelName()).isEqualTo("320i");
    }

    @Test
    void findByIdAndRemovedFalse_nonExistingId_returnsEmpty() {
        Optional<CarJpaEntity> result = carRepository.findByIdAndRemovedFalse(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void findByVinAndRemovedFalse_existingVin_returnsCar() {
        Optional<CarJpaEntity> result = carRepository.findByVinAndRemovedFalse(CAR_VIN);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(CAR_ID);
    }

    @Test
    void findByVinAndRemovedFalse_nonExistingVin_returnsEmpty() {
        Optional<CarJpaEntity> result = carRepository.findByVinAndRemovedFalse("NONEXISTENTVIN0000");

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByRemovedFalse_returnsSeedCars() {
        List<CarJpaEntity> cars = carRepository.findAllByRemovedFalse();

        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getId()).isEqualTo(CAR_ID);
    }

    @Test
    void findAllByTestDriveAvailableTrueAndRemovedFalse_returnsTestDriveCars() {
        List<CarJpaEntity> cars = carRepository.findAllByTestDriveAvailableTrueAndRemovedFalse();

        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getId()).isEqualTo(CAR_ID);
        assertThat(cars.get(0).isTestDriveAvailable()).isTrue();
    }

    @Test
    void markRemoved_carNoLongerReturnedByActiveFinder() {
        CarJpaEntity car = carRepository.findByIdAndRemovedFalse(CAR_ID).orElseThrow();
        car.markRemoved();
        carRepository.saveAndFlush(car);

        Optional<CarJpaEntity> result = carRepository.findByIdAndRemovedFalse(CAR_ID);

        assertThat(result).isEmpty();
    }
}
