package org.dealership.domain.model.configuration;

import org.dealership.domain.exception.DomainValidationException;
import org.dealership.domain.exception.IncompatibleComponentException;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.enums.ComponentType;
import org.dealership.domain.model.vo.Money;
import org.dealership.domain.validation.DomainChecks;

import java.util.Set;

public class Configuration {
    private final CarModel model;
    private final ComponentVariantSelection componentVariantSelection;

    public Configuration(
            CarModel model,
            ComponentVariantSelection componentVariantSelection
    ) {
        this.model = DomainChecks.notNull(model, "model");
        this.componentVariantSelection = DomainChecks.notNull(
                componentVariantSelection, "componentVariantSelection");
    }

    public Money getPrice() {
        Set<ComponentType> requiredTypes = model.getComponentTypes();
        if (!componentVariantSelection.isComplete(requiredTypes)) {
            throw new DomainValidationException(
                    "Missing required components: " + componentVariantSelection.missingTypes(requiredTypes));
        }
        Money total = model.getBasePrice();
        for (ComponentType type : model.getConfigurableComponentTypes()) {
            ComponentVariant variant = componentVariantSelection.getVariant(type);
            total = total.add(variant.getSurcharge());
        }
        return total;
    }

    public Configuration withVariant(ComponentVariant variant) {
        DomainChecks.notNull(variant, "variant");

        if (!model.getComponentTypes().contains(variant.getComponentType())) {
            throw new DomainValidationException(
                    "Component type " + variant.getComponentType() + " is not supported by the model.");
        }
        if (!model.isComponentConfigurable(variant.getComponentType())) {
            throw new DomainValidationException(
                    "Component type " + variant.getComponentType() + " is fixed for the model.");
        }
        if (!variant.isCompatibleWith(model.getId())) {
            throw new IncompatibleComponentException(
                    "Component " + variant.getName() + " is incompatible with the model.");
        }
        ComponentVariantSelection newCarConfig
                = componentVariantSelection.withVariant(variant.getComponentType(), variant);
        return new Configuration(model, newCarConfig);
    }

    public CarModel getModel() {
        return model;
    }

    public ComponentVariantSelection getComponentVariantSelection() {
        return componentVariantSelection;
    }

    public ComponentVariant getVariant(ComponentType type) {
        return componentVariantSelection.getVariant(type);
    }
}
