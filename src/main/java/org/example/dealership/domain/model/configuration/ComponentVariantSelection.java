package org.example.dealership.domain.model.configuration;

import org.example.dealership.domain.model.enums.ComponentType;
import org.example.dealership.domain.model.id.ComponentVariantId;
import org.example.dealership.domain.model.vo.Money;
import org.example.dealership.domain.validation.DomainChecks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentVariantSelection {
    private final Map<ComponentType, ComponentVariant> variants;

    public ComponentVariantSelection(Map<ComponentType, ComponentVariant> variants) {
        this.variants = Map.copyOf(DomainChecks.notNull(variants, "variants"));
    }

    public static ComponentVariantSelection empty() {
        return new ComponentVariantSelection(Map.of());
    }

    public ComponentVariant getVariant(ComponentType type) {
        return variants.get(type);
    }

    public boolean hasType(ComponentType type) {
        return variants.containsKey(type);
    }

    public Set<ComponentType> getComponentTypes() {
        return Set.copyOf(variants.keySet());
    }

    public Set<ComponentVariant> getVariants() {
        return Set.copyOf(variants.values());
    }

    public Map<ComponentType, ComponentVariant> asMap() {
        return Map.copyOf(variants);
    }

    public ComponentVariantSelection filterByTypes(Set<ComponentType> types) {
        DomainChecks.notNull(types, "types");
        if (types.isEmpty()) {
            return new ComponentVariantSelection(Map.of());
        }
        Map<ComponentType, ComponentVariant> filtered = variants.entrySet().stream()
                .filter(entry -> types.contains(entry.getKey()))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        return new ComponentVariantSelection(filtered);
    }

    public boolean isComplete(Set<ComponentType> requiredTypes) {
        return variants.keySet().containsAll(requiredTypes);
    }

    public Set<ComponentType> missingTypes(Set<ComponentType> requiredTypes) {
        return requiredTypes.stream()
                .filter(type -> !variants.containsKey(type))
                .collect(Collectors.toUnmodifiableSet());
    }

    public ComponentVariantSelection withVariant(ComponentType type, ComponentVariant variant) {
        Map<ComponentType, ComponentVariant> copy = new HashMap<>(variants);
        copy.put(type, variant);

        return new ComponentVariantSelection(copy);
    }
}
