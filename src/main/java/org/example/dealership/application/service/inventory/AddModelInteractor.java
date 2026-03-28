package org.example.dealership.application.service.inventory;

import org.example.dealership.application.mapping.CarModelMapper;
import org.example.dealership.application.port.in.inventory.AddModelUseCase;
import org.example.dealership.application.port.in.inventory.dto.NewModelDto;
import org.example.dealership.application.port.out.persistence.BrandRepository;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarModelId;

public class AddModelInteractor implements AddModelUseCase {
    private final CarModelRepository carModelRepository;
    private final BrandRepository brandRepository;

    public AddModelInteractor(
            CarModelRepository carModelRepository,
            BrandRepository brandRepository
    ) {
        this.carModelRepository = carModelRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public Response execute(Request request) {
        NewModelDto dto = request.newModel();
        BrandId brandId = new BrandId(dto.brandId());
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found: " + brandId));
        CarModelId modelId = carModelRepository.nextId();
        CarModel model = CarModelMapper.mapFromNewDto(dto, modelId, brand);

        carModelRepository.save(model);
        return new Response(modelId.value());
    }
}
