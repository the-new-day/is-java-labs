package org.dealership.infrastructure.rest.controller.inventory;

import org.dealership.application.port.in.inventory.*;
import org.dealership.application.port.in.inventory.dto.NewSparePartDto;
import org.dealership.application.port.in.inventory.dto.SparePartSummaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/spare-parts")
public class InventorySparePartController {

    private final AddSparePartUseCase addSparePartUseCase;
    private final GetSparePartUseCase getSparePartUseCase;
    private final ListSparePartsUseCase listSparePartsUseCase;
    private final UpdateSparePartUseCase updateSparePartUseCase;
    private final DeleteSparePartUseCase deleteSparePartUseCase;

    public InventorySparePartController(
            AddSparePartUseCase addSparePartUseCase,
            GetSparePartUseCase getSparePartUseCase,
            ListSparePartsUseCase listSparePartsUseCase,
            UpdateSparePartUseCase updateSparePartUseCase,
            DeleteSparePartUseCase deleteSparePartUseCase) {
        this.addSparePartUseCase = addSparePartUseCase;
        this.getSparePartUseCase = getSparePartUseCase;
        this.listSparePartsUseCase = listSparePartsUseCase;
        this.updateSparePartUseCase = updateSparePartUseCase;
        this.deleteSparePartUseCase = deleteSparePartUseCase;
    }

    @PostMapping
    public ResponseEntity<AddSparePartUseCase.Response> addSparePart(@RequestBody NewSparePartDto newSparePart) {
        AddSparePartUseCase.Response response = addSparePartUseCase.execute(new AddSparePartUseCase.Request(newSparePart));
        return ResponseEntity.created(URI.create("/api/inventory/spare-parts/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSparePartUseCase.Response> getSparePart(@PathVariable UUID id) {
        return ResponseEntity.ok(getSparePartUseCase.execute(new GetSparePartUseCase.Request(id)));
    }

    @GetMapping
    public ResponseEntity<ListSparePartsUseCase.Response> listSpareParts() {
        return ResponseEntity.ok(listSparePartsUseCase.execute(new ListSparePartsUseCase.Request()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSparePart(@PathVariable UUID id, @RequestBody SparePartSummaryDto sparePart) {
        updateSparePartUseCase.execute(new UpdateSparePartUseCase.Request(sparePart));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSparePart(@PathVariable UUID id) {
        deleteSparePartUseCase.execute(new DeleteSparePartUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
