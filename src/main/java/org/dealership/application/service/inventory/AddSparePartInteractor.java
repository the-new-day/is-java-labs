package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.AddSparePartUseCase;
import org.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.domain.model.id.SparePartId;
import org.dealership.domain.model.part.SparePart;

public class AddSparePartInteractor implements AddSparePartUseCase {
    private final SparePartRepository sparePartRepository;
    private final SparePartMapper sparePartMapper;

    public AddSparePartInteractor(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        this.sparePartRepository = sparePartRepository;
        this.sparePartMapper = sparePartMapper;
    }

    @Override
    public Response execute(Request request) {
        NewSparePartDto dto = request.newSparePart();
        SparePartId partId = sparePartRepository.nextId();
        SparePart part = sparePartMapper.toDomain(dto, partId);
        sparePartRepository.save(part);
        return new Response(partId.value());
    }
}
