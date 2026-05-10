package org.dealership.infrastructure.rest.controller.customorder;

import org.dealership.application.port.in.common.dto.ConfigurationDto;
import org.dealership.application.port.in.customorder.*;
import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.dealership.application.port.out.security.CurrentUserProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/custom-orders")
public class CustomOrderController {
    private final CreateCustomOrderUseCase createCustomOrderUseCase;
    private final GetCustomOrderUseCase getCustomOrderUseCase;
    private final ListCustomOrdersUseCase listCustomOrdersUseCase;
    private final UpdateCustomOrderUseCase updateCustomOrderUseCase;
    private final DeleteCustomOrderUseCase deleteCustomOrderUseCase;
    private final CurrentUserProvider currentUserProvider;

    public CustomOrderController(
            CreateCustomOrderUseCase createCustomOrderUseCase,
            GetCustomOrderUseCase getCustomOrderUseCase,
            ListCustomOrdersUseCase listCustomOrdersUseCase,
            UpdateCustomOrderUseCase updateCustomOrderUseCase,
            DeleteCustomOrderUseCase deleteCustomOrderUseCase,
            CurrentUserProvider currentUserProvider) {
        this.createCustomOrderUseCase = createCustomOrderUseCase;
        this.getCustomOrderUseCase = getCustomOrderUseCase;
        this.listCustomOrdersUseCase = listCustomOrdersUseCase;
        this.updateCustomOrderUseCase = updateCustomOrderUseCase;
        this.deleteCustomOrderUseCase = deleteCustomOrderUseCase;
        this.currentUserProvider = currentUserProvider;
    }

    public record CreateCustomOrderBody(ConfigurationDto configuration) {}

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateCustomOrderUseCase.Response> createCustomOrder(
            @RequestBody CreateCustomOrderBody body
    ) {
        UUID clientId = currentUserProvider.currentUserId().value();
        CreateCustomOrderUseCase.Response response = createCustomOrderUseCase.execute(
                new CreateCustomOrderUseCase.Request(clientId, body.configuration())
        );
        return ResponseEntity.created(URI.create("/api/custom-orders/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN') or @orderAccess.isCustomOrderOwner(#id, authentication)")
    public ResponseEntity<GetCustomOrderUseCase.Response> getCustomOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(getCustomOrderUseCase.execute(new GetCustomOrderUseCase.Request(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public ResponseEntity<ListCustomOrdersUseCase.Response> listCustomOrders() {
        UUID clientFilter = isManagerOrAdmin() ? null : currentUserProvider.currentUserId().value();
        return ResponseEntity.ok(listCustomOrdersUseCase.execute(new ListCustomOrdersUseCase.Request(clientFilter)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<Void> updateCustomOrder(@PathVariable UUID id, @RequestBody CustomOrderDto order) {
        updateCustomOrderUseCase.execute(new UpdateCustomOrderUseCase.Request(order));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderAccess.isCustomOrderOwner(#id, authentication)")
    public ResponseEntity<Void> deleteCustomOrder(@PathVariable UUID id) {
        deleteCustomOrderUseCase.execute(new DeleteCustomOrderUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }

    private static boolean isManagerOrAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> "ROLE_MANAGER".equals(a) || "ROLE_ADMIN".equals(a));
    }
}
