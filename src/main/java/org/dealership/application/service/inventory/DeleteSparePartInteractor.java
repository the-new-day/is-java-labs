package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.DeleteSparePartUseCase;
import org.dealership.application.port.out.persistence.SparePartRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.id.SparePartId;

public class DeleteSparePartInteractor implements DeleteSparePartUseCase {
    private final SparePartRepository sparePartRepository;

    public DeleteSparePartInteractor(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public Response execute(Request request) {
        SparePartId partId = new SparePartId(request.id());
        if (sparePartRepository.findById(partId).isEmpty()) {
            throw new EntityNotFoundException("Spare part not found: " + partId);
        }
        sparePartRepository.deleteById(partId);
        return new Response();
    }
}
