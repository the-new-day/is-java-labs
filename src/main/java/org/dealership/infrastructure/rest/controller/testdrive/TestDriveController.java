package org.dealership.infrastructure.rest.controller.testdrive;

import org.dealership.application.port.in.testdrive.*;
import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/test-drives")
public class TestDriveController {
    private final CreateTestDriveRequestUseCase createTestDriveRequestUseCase;
    private final GetTestDriveRequestUseCase getTestDriveRequestUseCase;
    private final ListTestDriveRequestsUseCase listTestDriveRequestsUseCase;
    private final UpdateTestDriveRequestUseCase updateTestDriveRequestUseCase;
    private final DeleteTestDriveRequestUseCase deleteTestDriveRequestUseCase;
    private final SetTestDriveAvailableUseCase setTestDriveAvailableUseCase;

    public TestDriveController(
            CreateTestDriveRequestUseCase createTestDriveRequestUseCase,
            GetTestDriveRequestUseCase getTestDriveRequestUseCase,
            ListTestDriveRequestsUseCase listTestDriveRequestsUseCase,
            UpdateTestDriveRequestUseCase updateTestDriveRequestUseCase,
            DeleteTestDriveRequestUseCase deleteTestDriveRequestUseCase,
            SetTestDriveAvailableUseCase setTestDriveAvailableUseCase
    ) {
        this.createTestDriveRequestUseCase = createTestDriveRequestUseCase;
        this.getTestDriveRequestUseCase = getTestDriveRequestUseCase;
        this.listTestDriveRequestsUseCase = listTestDriveRequestsUseCase;
        this.updateTestDriveRequestUseCase = updateTestDriveRequestUseCase;
        this.deleteTestDriveRequestUseCase = deleteTestDriveRequestUseCase;
        this.setTestDriveAvailableUseCase = setTestDriveAvailableUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateTestDriveRequestUseCase.Response> createTestDriveRequest(
            @RequestBody CreateTestDriveRequestUseCase.Request request
    ) {
        CreateTestDriveRequestUseCase.Response response = createTestDriveRequestUseCase.execute(request);
        return ResponseEntity.created(URI.create("/api/test-drives/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTestDriveRequestUseCase.Response> getTestDriveRequest(@PathVariable UUID id) {
        return ResponseEntity.ok(getTestDriveRequestUseCase.execute(new GetTestDriveRequestUseCase.Request(id)));
    }

    @GetMapping
    public ResponseEntity<ListTestDriveRequestsUseCase.Response> listTestDriveRequests() {
        return ResponseEntity.ok(listTestDriveRequestsUseCase.execute(new ListTestDriveRequestsUseCase.Request()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTestDriveRequest(
            @PathVariable UUID id,
            @RequestBody TestDriveRequestDto request
    ) {
        updateTestDriveRequestUseCase.execute(new UpdateTestDriveRequestUseCase.Request(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestDriveRequest(@PathVariable UUID id) {
        deleteTestDriveRequestUseCase.execute(new DeleteTestDriveRequestUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cars/{carId}/availability")
    public ResponseEntity<Void> setTestDriveAvailable(
            @PathVariable UUID carId,
            @RequestParam boolean available
    ) {
        setTestDriveAvailableUseCase.execute(new SetTestDriveAvailableUseCase.Request(carId, available));
        return ResponseEntity.ok().build();
    }
}
