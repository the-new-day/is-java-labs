package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.DeleteSparePartUseCase;
import org.example.dealership.application.port.out.persistence.SparePartRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.SparePartId;

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
