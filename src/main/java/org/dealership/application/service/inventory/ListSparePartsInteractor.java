package org.dealership.application.service.inventory;

import org.dealership.application.mapping.SparePartMapper;
import org.dealership.application.port.in.inventory.ListSparePartsUseCase;
import org.dealership.application.port.out.persistence.SparePartRepository;

public class ListSparePartsInteractor implements ListSparePartsUseCase {
    private final SparePartRepository sparePartRepository;

    public ListSparePartsInteractor(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                sparePartRepository.findAll().stream()
                        .map(SparePartMapper::mapToSummaryDto)
                        .toList()
        );
    }
}
