package org.dealership.infrastructure.rest.controller.inventory;

import org.dealership.application.port.in.inventory.*;
import org.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.dealership.application.port.in.common.dto.CarDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/cars")
@PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
public class InventoryCarController {
    private final AddCarUseCase addCarUseCase;
    private final GetInventoryCarUseCase getInventoryCarUseCase;
    private final ListInventoryCarsUseCase listInventoryCarsUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final DeleteCarUseCase deleteCarUseCase;

    public InventoryCarController(
            AddCarUseCase addCarUseCase,
            GetInventoryCarUseCase getInventoryCarUseCase,
            ListInventoryCarsUseCase listInventoryCarsUseCase,
            UpdateCarUseCase updateCarUseCase,
            DeleteCarUseCase deleteCarUseCase) {
        this.addCarUseCase = addCarUseCase;
        this.getInventoryCarUseCase = getInventoryCarUseCase;
        this.listInventoryCarsUseCase = listInventoryCarsUseCase;
        this.updateCarUseCase = updateCarUseCase;
        this.deleteCarUseCase = deleteCarUseCase;
    }

    @PostMapping
    public ResponseEntity<AddCarUseCase.Response> addCar(@RequestBody NewCarDetailsDto newCar) {
        AddCarUseCase.Response response = addCarUseCase.execute(new AddCarUseCase.Request(newCar));
        return ResponseEntity.created(URI.create("/api/inventory/cars/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetInventoryCarUseCase.Response> getCar(@PathVariable UUID id) {
        return ResponseEntity.ok(getInventoryCarUseCase.execute(new GetInventoryCarUseCase.Request(id)));
    }

    @GetMapping
    public ResponseEntity<ListInventoryCarsUseCase.Response> listCars() {
        return ResponseEntity.ok(listInventoryCarsUseCase.execute(new ListInventoryCarsUseCase.Request()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable UUID id, @RequestBody CarDetailsDto car) {
        updateCarUseCase.execute(new UpdateCarUseCase.Request(car));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        deleteCarUseCase.execute(new DeleteCarUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
