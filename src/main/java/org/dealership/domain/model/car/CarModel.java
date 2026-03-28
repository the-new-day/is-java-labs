package org.dealership.domain.model.car;

import org.dealership.domain.model.configuration.ComponentVariantSelection;
import org.dealership.domain.model.enums.*;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.vo.Money;
import org.dealership.domain.validation.DomainChecks;

import java.util.HashSet;
import java.util.Set;

public class CarModel {
    private final CarModelId id;
    private final Brand brand;
    private final String modelName;
    private final Money basePrice;
    private final CarBodyType carBodyType;
    private final FuelType fuelType;
    private final DriveType driveType;
    private final double engineVolume;
    private final int enginePower;
    private final TransmissionType baseTransmissionType;
    private final ComponentVariantSelection baseComponentSelection;
    private final Set<ComponentType> configurableComponentTypes;

    public CarModel(
            CarModelId id,
            Brand brand,
            String modelName,
            Money basePrice,
            CarBodyType carBodyType,
            FuelType fuelType,
            DriveType driveType,
            double engineVolume,
            int enginePower,
            TransmissionType baseTransmissionType,
            ComponentVariantSelection baseComponentSelection,
            Set<ComponentType> configurableComponentTypes
    ) {
        this.id = DomainChecks.notNull(id, "modelId");
        this.brand = DomainChecks.notNull(brand, "brand");
        this.modelName = DomainChecks.notBlank(modelName, "modelName");
        this.basePrice = DomainChecks.notNull(basePrice, "basePrice");
        this.carBodyType = DomainChecks.notNull(carBodyType, "carBodyType");
        this.fuelType = DomainChecks.notNull(fuelType, "fuelType");
        this.driveType = DomainChecks.notNull(driveType, "driveType");
        this.engineVolume = DomainChecks.positive(engineVolume, "engineVolume");
        this.enginePower = DomainChecks.positive(enginePower, "enginePower");
        this.baseComponentSelection = DomainChecks.notNull(baseComponentSelection, "baseConfiguration");
        this.baseTransmissionType = DomainChecks.notNull(baseTransmissionType, "baseTransmissionType");
        this.configurableComponentTypes = Set.copyOf(
                DomainChecks.notNull(configurableComponentTypes, "configurableComponentTypes"));

        DomainChecks.require(
                baseComponentSelection.getComponentTypes().containsAll(this.configurableComponentTypes),
                "All configurable component types must be present in base configuration."
        );
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public String getModelName() {
        return modelName;
    }

    public CarModelId getId() {
        return id;
    }

    public ComponentVariantSelection getBaseComponentSelection() {
        return baseComponentSelection;
    }

    public CarBodyType getCarBodyType() {
        return carBodyType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public Set<ComponentType> getComponentTypes() {
        return baseComponentSelection.getComponentTypes();
    }

    public Set<ComponentType> getConfigurableComponentTypes() {
        return Set.copyOf(configurableComponentTypes);
    }

    public boolean isComponentConfigurable(ComponentType type) {
        return configurableComponentTypes.contains(type);
    }

    public Set<ComponentType> getFixedComponentTypes() {
        Set<ComponentType> fixed = new HashSet<>(baseComponentSelection.getComponentTypes());
        fixed.removeAll(configurableComponentTypes);
        return fixed;
    }

    public ComponentVariantSelection getFixedConfiguration() {
        return baseComponentSelection.filterByTypes(getFixedComponentTypes());
    }

    public Brand getBrand() {
        return brand;
    }

    public TransmissionType getBaseTransmissionType() {
        return baseTransmissionType;
    }
}
