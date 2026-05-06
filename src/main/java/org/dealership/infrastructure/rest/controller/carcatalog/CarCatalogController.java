package org.dealership.infrastructure.rest.controller.carcatalog;

import org.dealership.application.port.in.carcatalog.GetCarUseCase;
import org.dealership.application.port.in.carcatalog.GetModelUseCase;
import org.dealership.application.port.in.carcatalog.ListCarsUseCase;
import org.dealership.application.port.in.carcatalog.ListModelsUseCase;
import org.dealership.application.port.in.carcatalog.dto.CarFilterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/catalog")
public class CarCatalogController {
    private final GetCarUseCase getCarUseCase;
    private final ListCarsUseCase listCarsUseCase;
    private final GetModelUseCase getModelUseCase;
    private final ListModelsUseCase listModelsUseCase;

    public CarCatalogController(
            GetCarUseCase getCarUseCase,
            ListCarsUseCase listCarsUseCase,
            GetModelUseCase getModelUseCase,
            ListModelsUseCase listModelsUseCase) {
        this.getCarUseCase = getCarUseCase;
        this.listCarsUseCase = listCarsUseCase;
        this.getModelUseCase = getModelUseCase;
        this.listModelsUseCase = listModelsUseCase;
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<GetCarUseCase.Response> getCar(@PathVariable UUID id) {
        return ResponseEntity.ok(getCarUseCase.execute(new GetCarUseCase.Request(id)));
    }

    @GetMapping("/cars")
    public ResponseEntity<ListCarsUseCase.Response> listCars(
            @RequestBody(required = false) CarFilterDto filter
    ) {
        return ResponseEntity.ok(listCarsUseCase.execute(new ListCarsUseCase.Request(filter)));
    }

    @GetMapping("/models/{id}")
    public ResponseEntity<GetModelUseCase.Response> getModel(@PathVariable UUID id) {
        return ResponseEntity.ok(getModelUseCase.execute(new GetModelUseCase.Request(id)));
    }

    @GetMapping("/models")
    public ResponseEntity<ListModelsUseCase.Response> listModels() {
        return ResponseEntity.ok(listModelsUseCase.execute(new ListModelsUseCase.Request()));
    }
}
