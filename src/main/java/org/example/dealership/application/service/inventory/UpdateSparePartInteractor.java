package org.example.dealership.application.service.inventory;

import org.example.dealership.application.mapping.SparePartMapper;
import org.example.dealership.application.port.in.inventory.UpdateSparePartUseCase;
import org.example.dealership.application.port.in.inventory.dto.SparePartSummary;
import org.example.dealership.application.port.out.persistence.SparePartRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.SparePartId;

public class UpdateSparePartInteractor implements UpdateSparePartUseCase {
    private final SparePartRepository sparePartRepository;

    public UpdateSparePartInteractor(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public Response execute(Request request) {
        SparePartSummary dto = request.sparePartSummary();
        SparePartId partId = new SparePartId(dto.id());
        if (sparePartRepository.findById(partId).isEmpty()) {
            throw new EntityNotFoundException("Spare part not found: " + partId);
        }
        sparePartRepository.save(SparePartMapper.mapFromDto(dto));
        return new Response();
    }
}
