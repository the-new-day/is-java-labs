package org.example.dealership.domain.model.car;

import org.example.dealership.domain.model.configuration.ComponentVariant;
import org.example.dealership.domain.model.configuration.ComponentVariantSelection;
import org.example.dealership.domain.model.configuration.Configuration;
import org.example.dealership.domain.model.enums.Color;
import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.CarId;
import org.example.dealership.domain.model.vo.Money;
import org.example.dealership.domain.model.vo.VinNumber;
import org.example.dealership.domain.validation.DomainChecks;

public class Car {
    private final CarId id;
    private final VinNumber vinNumber;
    private final Configuration configuration;
    private final Color color;
    private final boolean testDriveAvailable;

    public Car(
            CarId id,
            VinNumber vinNumber,
            Configuration configuration,
            Color color,
            boolean testDriveAvailable
    ) {
        this.id = DomainChecks.notNull(id, "carId");
        this.vinNumber = DomainChecks.notNull(vinNumber, "vinNumber");
        this.color = DomainChecks.notNull(color, "color");
        this.testDriveAvailable = testDriveAvailable;

        this.configuration = DomainChecks.notNull(configuration, "configuration");
    }

    public Money getPrice() {
        return configuration.getPrice();
    }

    public CarId getId() {
        return id;
    }

    public CarModel getModel() {
        return getConfiguration().getModel();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Color getColor() {
        return color;
    }

    public VinNumber getVinNumber() {
        return vinNumber;
    }

    public boolean isTestDriveAvailable() {
        return testDriveAvailable;
    }

    public Car withTestDriveAvailability(boolean available) {
        return new Car(id, vinNumber, configuration, color, available);
    }
}
