package org.dealership.infrastructure.rest.controller.inventory;

import org.dealership.application.port.in.inventory.GetAvailableCarUseCase;
import org.dealership.application.port.in.inventory.ListAvailableCarsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
public class AvailableCarController {

    private final ListAvailableCarsUseCase listAvailableCarsUseCase;
    private final GetAvailableCarUseCase getAvailableCarUseCase;

    public AvailableCarController(
            ListAvailableCarsUseCase listAvailableCarsUseCase,
            GetAvailableCarUseCase getAvailableCarUseCase
    ) {
        this.listAvailableCarsUseCase = listAvailableCarsUseCase;
        this.getAvailableCarUseCase = getAvailableCarUseCase;
    }

    @GetMapping
    public ResponseEntity<ListAvailableCarsUseCase.Response> listCars() {
        return ResponseEntity.ok(listAvailableCarsUseCase.execute(new ListAvailableCarsUseCase.Request()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAvailableCarUseCase.Response> getCar(@PathVariable UUID id) {
        return ResponseEntity.ok(getAvailableCarUseCase.execute(new GetAvailableCarUseCase.Request(id)));
    }
}
