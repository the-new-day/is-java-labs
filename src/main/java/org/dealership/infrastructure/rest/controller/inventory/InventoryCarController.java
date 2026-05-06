package org.dealership.infrastructure.rest.controller.inventory;

import org.dealership.application.port.in.inventory.*;
import org.dealership.application.port.in.inventory.dto.NewCarDetailsDto;
import org.dealership.application.port.in.common.dto.CarDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/cars")
public class InventoryCarController {
    private final AddCarUseCase addCarUseCase;
    private final GetCarUseCase getCarUseCase;
    private final ListCarsUseCase listCarsUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final DeleteCarUseCase deleteCarUseCase;

    public InventoryCarController(
            AddCarUseCase addCarUseCase,
            GetCarUseCase getCarUseCase,
            ListCarsUseCase listCarsUseCase,
            UpdateCarUseCase updateCarUseCase,
            DeleteCarUseCase deleteCarUseCase) {
        this.addCarUseCase = addCarUseCase;
        this.getCarUseCase = getCarUseCase;
        this.listCarsUseCase = listCarsUseCase;
        this.updateCarUseCase = updateCarUseCase;
        this.deleteCarUseCase = deleteCarUseCase;
    }

    @PostMapping
    public ResponseEntity<AddCarUseCase.Response> addCar(@RequestBody NewCarDetailsDto newCar) {
        AddCarUseCase.Response response = addCarUseCase.execute(new AddCarUseCase.Request(newCar));
        return ResponseEntity.created(URI.create("/api/inventory/cars/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCarUseCase.Response> getCar(@PathVariable UUID id) {
        return ResponseEntity.ok(getCarUseCase.execute(new GetCarUseCase.Request(id)));
    }

    @GetMapping
    public ResponseEntity<ListCarsUseCase.Response> listCars() {
        return ResponseEntity.ok(listCarsUseCase.execute(new ListCarsUseCase.Request()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable UUID id, @RequestBody CarDetailsDto car) {
        updateCarUseCase.execute(new UpdateCarUseCase.Request(car));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        deleteCarUseCase.execute(new DeleteCarUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
