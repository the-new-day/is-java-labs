package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.GetSparePartUseCase;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.SparePartId;

public class GetSparePartInteractor implements GetSparePartUseCase {
    private final SparePartRepository sparePartRepository;
    private final SparePartMapper sparePartMapper;

    public GetSparePartInteractor(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        this.sparePartRepository = sparePartRepository;
        this.sparePartMapper = sparePartMapper;
    }

    @Override
    public Response execute(Request request) {
        SparePartId partId = new SparePartId(request.sparePartId());
        return sparePartRepository.findById(partId)
                .map(sparePartMapper::toSummaryDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Spare part not found: " + partId));
    }
}
