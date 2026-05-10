package org.dealership.infrastructure.persistence.jpa.specification;

import jakarta.persistence.criteria.*;
import org.dealership.domain.model.carfilter.CarFilter;
import org.dealership.infrastructure.persistence.jpa.entity.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarSpecificationConverter {
    public Specification<CarJpaEntity> convertFromFilter(CarFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isFalse(root.get("removed")));

            Join<CarJpaEntity, ConfigurationJpaEntity> configJoin =
                    root.join("configuration", JoinType.INNER);
            Join<ConfigurationJpaEntity, CarModelJpaEntity> modelJoin =
                    configJoin.join("carModel", JoinType.INNER);

            addColorPredicate(predicates, root, cb, filter);
            addBrandPredicate(predicates, modelJoin, cb, filter);
            addModelPredicate(predicates, modelJoin, cb, filter);
            addBodyTypePredicate(predicates, modelJoin, cb, filter);
            addFuelTypePredicate(predicates, modelJoin, cb, filter);
            addDriveTypePredicate(predicates, modelJoin, cb, filter);
            addTransmissionTypePredicate(predicates, modelJoin, cb, filter);
            addEnginePowerPredicates(predicates, modelJoin, cb, filter);
            addEngineVolumePredicates(predicates, modelJoin, cb, filter);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void addColorPredicate(List<Predicate> predicates, Root<CarJpaEntity> root,
                                   CriteriaBuilder cb, CarFilter filter) {
        if (filter.color() != null) {
            predicates.add(cb.equal(root.get("color"), filter.color()));
        }
    }

    private void addBrandPredicate(List<Predicate> predicates,
                                   Join<?, CarModelJpaEntity> modelJoin,
                                   CriteriaBuilder cb, CarFilter filter) {
        if (filter.brand() != null) {
            Join<CarModelJpaEntity, ?> brandJoin = modelJoin.join("brand", JoinType.INNER);
            predicates.add(cb.equal(brandJoin.get("id"), filter.brand().value()));
        }
    }

    private void addModelPredicate(List<Predicate> predicates,
                                   Join<?, CarModelJpaEntity> modelJoin,
                                   CriteriaBuilder cb, CarFilter filter) {
        if (filter.model() != null) {
            predicates.add(cb.equal(modelJoin.get("id"), filter.model().value()));
        }
    }

    private void addBodyTypePredicate(List<Predicate> predicates,
                                      Join<?, CarModelJpaEntity> modelJoin,
                                      CriteriaBuilder cb, CarFilter filter) {
        if (filter.bodyType() != null) {
            predicates.add(cb.equal(modelJoin.get("bodyType"), filter.bodyType()));
        }
    }

    private void addFuelTypePredicate(List<Predicate> predicates,
                                      Join<?, CarModelJpaEntity> modelJoin,
                                      CriteriaBuilder cb, CarFilter filter) {
        if (filter.fuelType() != null) {
            predicates.add(cb.equal(modelJoin.get("fuelType"), filter.fuelType()));
        }
    }

    private void addDriveTypePredicate(List<Predicate> predicates,
                                       Join<?, CarModelJpaEntity> modelJoin,
                                       CriteriaBuilder cb, CarFilter filter) {
        if (filter.driveType() != null) {
            predicates.add(cb.equal(modelJoin.get("driveType"), filter.driveType()));
        }
    }

    private void addTransmissionTypePredicate(List<Predicate> predicates,
                                              Join<?, CarModelJpaEntity> modelJoin,
                                              CriteriaBuilder cb, CarFilter filter) {
        if (filter.transmissionType() != null) {
            predicates.add(cb.equal(modelJoin.get("baseTransmissionType"), filter.transmissionType()));
        }
    }

    private void addEnginePowerPredicates(List<Predicate> predicates,
                                          Join<?, CarModelJpaEntity> modelJoin,
                                          CriteriaBuilder cb, CarFilter filter) {
        if (filter.minEnginePower() != null) {
            predicates.add(cb.greaterThanOrEqualTo(
                    modelJoin.get("enginePower"), filter.minEnginePower()));
        }
        if (filter.maxEnginePower() != null) {
            predicates.add(cb.lessThanOrEqualTo(
                    modelJoin.get("enginePower"), filter.maxEnginePower()));
        }
    }

    private void addEngineVolumePredicates(List<Predicate> predicates,
                                           Join<?, CarModelJpaEntity> modelJoin,
                                           CriteriaBuilder cb, CarFilter filter) {
        if (filter.minEngineVolume() != null) {
            predicates.add(cb.greaterThanOrEqualTo(
                    modelJoin.get("engineVolume"), filter.minEngineVolume()));
        }
        if (filter.maxEngineVolume() != null) {
            predicates.add(cb.lessThanOrEqualTo(
                    modelJoin.get("engineVolume"), filter.maxEngineVolume()));
        }
    }
}