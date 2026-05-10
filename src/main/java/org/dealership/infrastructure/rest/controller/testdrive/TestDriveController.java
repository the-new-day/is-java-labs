package org.dealership.infrastructure.rest.controller.testdrive;

import org.dealership.application.port.in.testdrive.*;
import org.dealership.application.port.in.testdrive.dto.TestDriveRequestDto;
import org.dealership.application.port.out.security.CurrentUserProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
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
    private final CurrentUserProvider currentUserProvider;

    public TestDriveController(
            CreateTestDriveRequestUseCase createTestDriveRequestUseCase,
            GetTestDriveRequestUseCase getTestDriveRequestUseCase,
            ListTestDriveRequestsUseCase listTestDriveRequestsUseCase,
            UpdateTestDriveRequestUseCase updateTestDriveRequestUseCase,
            DeleteTestDriveRequestUseCase deleteTestDriveRequestUseCase,
            SetTestDriveAvailableUseCase setTestDriveAvailableUseCase,
            CurrentUserProvider currentUserProvider
    ) {
        this.createTestDriveRequestUseCase = createTestDriveRequestUseCase;
        this.getTestDriveRequestUseCase = getTestDriveRequestUseCase;
        this.listTestDriveRequestsUseCase = listTestDriveRequestsUseCase;
        this.updateTestDriveRequestUseCase = updateTestDriveRequestUseCase;
        this.deleteTestDriveRequestUseCase = deleteTestDriveRequestUseCase;
        this.setTestDriveAvailableUseCase = setTestDriveAvailableUseCase;
        this.currentUserProvider = currentUserProvider;
    }

    public record CreateTestDriveBody(UUID carId, LocalDateTime startsAt) {}

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateTestDriveRequestUseCase.Response> createTestDriveRequest(
            @RequestBody CreateTestDriveBody body
    ) {
        UUID clientId = currentUserProvider.currentUserId().value();
        CreateTestDriveRequestUseCase.Response response = createTestDriveRequestUseCase.execute(
                new CreateTestDriveRequestUseCase.Request(clientId, body.carId(), body.startsAt())
        );
        return ResponseEntity.created(URI.create("/api/test-drives/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN') or @orderAccess.isTestDriveOwner(#id, authentication)")
    public ResponseEntity<GetTestDriveRequestUseCase.Response> getTestDriveRequest(@PathVariable UUID id) {
        return ResponseEntity.ok(getTestDriveRequestUseCase.execute(new GetTestDriveRequestUseCase.Request(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public ResponseEntity<ListTestDriveRequestsUseCase.Response> listTestDriveRequests() {
        UUID clientFilter = isManagerOrAdmin() ? null : currentUserProvider.currentUserId().value();
        return ResponseEntity.ok(
                listTestDriveRequestsUseCase.execute(new ListTestDriveRequestsUseCase.Request(clientFilter))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<Void> updateTestDriveRequest(
            @PathVariable UUID id,
            @RequestBody TestDriveRequestDto request
    ) {
        updateTestDriveRequestUseCase.execute(new UpdateTestDriveRequestUseCase.Request(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderAccess.isTestDriveOwner(#id, authentication)")
    public ResponseEntity<Void> deleteTestDriveRequest(@PathVariable UUID id) {
        deleteTestDriveRequestUseCase.execute(new DeleteTestDriveRequestUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cars/{carId}/availability")
    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    public ResponseEntity<Void> setTestDriveAvailable(
            @PathVariable UUID carId,
            @RequestParam boolean available
    ) {
        setTestDriveAvailableUseCase.execute(new SetTestDriveAvailableUseCase.Request(carId, available));
        return ResponseEntity.ok().build();
    }

    private static boolean isManagerOrAdmin() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> "ROLE_MANAGER".equals(a) || "ROLE_ADMIN".equals(a));
    }
}
