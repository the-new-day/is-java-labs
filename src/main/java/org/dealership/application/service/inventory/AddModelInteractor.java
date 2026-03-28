package org.dealership.application.service.inventory;

import org.dealership.application.mapping.CarModelMapper;
import org.dealership.application.port.in.inventory.AddModelUseCase;
import org.dealership.application.port.in.inventory.dto.NewModelDto;
import org.dealership.application.port.out.persistence.BrandRepository;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.id.BrandId;
import org.dealership.domain.model.id.CarModelId;

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
