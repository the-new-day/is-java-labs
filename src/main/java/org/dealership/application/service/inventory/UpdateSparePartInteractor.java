package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.UpdateSparePartUseCase;
import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.SparePartId;

public class UpdateSparePartInteractor implements UpdateSparePartUseCase {
    private final SparePartRepository sparePartRepository;
    private final SparePartMapper sparePartMapper;

    public UpdateSparePartInteractor(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        this.sparePartRepository = sparePartRepository;
        this.sparePartMapper = sparePartMapper;
    }

    @Override
    public Response execute(Request request) {
        SparePartSummaryDto dto = request.sparePartSummaryDto();
        SparePartId partId = new SparePartId(dto.id());
        sparePartRepository.findById(partId)
                .orElseThrow(() -> new EntityNotFoundException("Spare part not found: " + partId));
        sparePartRepository.save(sparePartMapper.toDomain(dto));
        return new Response();
    }
}
