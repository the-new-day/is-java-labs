package org.dealership.application.service.inventory;

import org.dealership.application.mapper.SparePartMapper;
import org.dealership.application.port.in.inventory.ListSparePartsUseCase;
import org.dealership.application.port.out.persistence.SparePartRepository;

public class ListSparePartsInteractor implements ListSparePartsUseCase {
    private final SparePartRepository sparePartRepository;
    private final SparePartMapper sparePartMapper;

    public ListSparePartsInteractor(SparePartRepository sparePartRepository, SparePartMapper sparePartMapper) {
        this.sparePartRepository = sparePartRepository;
        this.sparePartMapper = sparePartMapper;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                sparePartRepository.findAll().stream()
                        .map(sparePartMapper::toSummaryDto)
                        .toList()
        );
    }
}
