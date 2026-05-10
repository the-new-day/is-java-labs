package org.dealership.infrastructure.rest.controller.inventory;

import org.dealership.application.port.in.inventory.*;
import org.dealership.application.port.in.inventory.dto.NewModelDto;
import org.dealership.application.port.in.common.dto.CarModelDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/models")
@PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
public class InventoryModelController {
    private final AddModelUseCase addModelUseCase;
    private final UpdateModelUseCase updateModelUseCase;
    private final DeleteModelUseCase deleteModelUseCase;

    public InventoryModelController(
            AddModelUseCase addModelUseCase,
            UpdateModelUseCase updateModelUseCase,
            DeleteModelUseCase deleteModelUseCase) {
        this.addModelUseCase = addModelUseCase;
        this.updateModelUseCase = updateModelUseCase;
        this.deleteModelUseCase = deleteModelUseCase;
    }

    @PostMapping
    public ResponseEntity<AddModelUseCase.Response> addModel(@RequestBody NewModelDto newModel) {
        AddModelUseCase.Response response = addModelUseCase.execute(new AddModelUseCase.Request(newModel));
        return ResponseEntity.created(URI.create("/api/inventory/models/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateModel(@PathVariable UUID id, @RequestBody CarModelDto model) {
        updateModelUseCase.execute(new UpdateModelUseCase.Request(model));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteModel(@PathVariable UUID id) {
        deleteModelUseCase.execute(new DeleteModelUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
