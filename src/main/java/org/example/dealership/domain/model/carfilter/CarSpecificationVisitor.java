package org.example.dealership.domain.model.carfilter;

import org.example.dealership.domain.model.carfilter.spec.*;

public interface CarSpecificationVisitor {
    void visit(PriceRangeSpecification spec);
    void visit(BrandSpecification spec);
    void visit(ModelSpecification spec);
    void visit(BodyTypeSpecification spec);
    void visit(FuelTypeSpecification spec);
    void visit(EnginePowerSpecification spec);
    void visit(EngineVolumeSpecification spec);
    void visit(TransmissionTypeSpecification spec);
    void visit(DriveTypeSpecification spec);
    void visit(ColorSpecification spec);
}
