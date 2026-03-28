package org.example.dealership.application.service.inventory;

import org.example.dealership.application.mapping.SparePartMapper;
import org.example.dealership.application.port.in.inventory.AddSparePartUseCase;
import org.example.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.example.dealership.application.port.out.persistence.SparePartRepository;
import org.example.dealership.domain.model.id.SparePartId;
import org.example.dealership.domain.model.part.SparePart;

public class AddSparePartInteractor implements AddSparePartUseCase {
    private final SparePartRepository sparePartRepository;

    public AddSparePartInteractor(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public Response execute(Request request) {
        NewSparePartDto dto = request.newSparePart();
        SparePartId partId = sparePartRepository.nextId();
        SparePart part = SparePartMapper.mapFromNewDto(dto, partId);
        sparePartRepository.save(part);
        return new Response(partId.value());
    }
}
