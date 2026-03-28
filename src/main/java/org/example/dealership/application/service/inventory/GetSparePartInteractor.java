package org.example.dealership.application.service.inventory;

import org.example.dealership.application.mapping.SparePartMapper;
import org.example.dealership.application.port.in.inventory.GetSparePartUseCase;
import org.example.dealership.application.port.out.persistence.SparePartRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.SparePartId;

public class GetSparePartInteractor implements GetSparePartUseCase {
    private final SparePartRepository sparePartRepository;

    public GetSparePartInteractor(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public Response execute(Request request) {
        SparePartId partId = new SparePartId(request.id());
        return sparePartRepository.findById(partId)
                .map(SparePartMapper::mapToSummaryDto)
                .map(Response::new)
                .orElseThrow(() -> new EntityNotFoundException("Spare part not found: " + partId));
    }
}
