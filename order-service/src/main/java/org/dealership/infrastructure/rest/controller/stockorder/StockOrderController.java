package org.dealership.infrastructure.rest.controller.stockorder;

import org.dealership.application.port.in.stockorder.*;
import org.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.dealership.application.port.in.stockorder.dto.UpdateStockOrderDto;
import org.dealership.application.port.out.security.CurrentUserProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-orders")
public class StockOrderController {
    private final CreateStockOrderUseCase createStockOrderUseCase;
    private final GetStockOrderUseCase getStockOrderUseCase;
    private final ListStockOrdersUseCase listStockOrdersUseCase;
    private final ListClientStockOrdersUseCase listClientStockOrdersUseCase;
    private final UpdateStockOrderUseCase updateStockOrderUseCase;
    private final DeleteStockOrderUseCase deleteStockOrderUseCase;
    private final CurrentUserProvider currentUserProvider;

    public StockOrderController(
            CreateStockOrderUseCase createStockOrderUseCase,
            GetStockOrderUseCase getStockOrderUseCase,
            ListStockOrdersUseCase listStockOrdersUseCase,
            ListClientStockOrdersUseCase listClientStockOrdersUseCase,
            UpdateStockOrderUseCase updateStockOrderUseCase,
            DeleteStockOrderUseCase deleteStockOrderUseCase,
            CurrentUserProvider currentUserProvider
    ) {
        this.createStockOrderUseCase = createStockOrderUseCase;
        this.getStockOrderUseCase = getStockOrderUseCase;
        this.listStockOrdersUseCase = listStockOrdersUseCase;
        this.listClientStockOrdersUseCase = listClientStockOrdersUseCase;
        this.updateStockOrderUseCase = updateStockOrderUseCase;
        this.deleteStockOrderUseCase = deleteStockOrderUseCase;
        this.currentUserProvider = currentUserProvider;
    }

    public record CreateStockOrderRequest(UUID carId) {}

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateStockOrderUseCase.Response> createStockOrder(
            @RequestBody CreateStockOrderRequest body
    ) {
        UUID clientId = currentUserProvider.currentUserId().value();
        CreateStockOrderUseCase.Response response = createStockOrderUseCase.execute(
                new CreateStockOrderUseCase.Request(clientId, body.carId())
        );
        return ResponseEntity.created(URI.create("/api/stock-orders/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN') or @orderAccess.isStockOrderOwner(#id, authentication)")
    public ResponseEntity<GetStockOrderUseCase.Response> getStockOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(getStockOrderUseCase.execute(new GetStockOrderUseCase.Request(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<ListStockOrdersUseCase.Response> listStockOrders() {
        return ResponseEntity.ok(listStockOrdersUseCase.execute(new ListStockOrdersUseCase.Request()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ListClientStockOrdersUseCase.Response> listMyStockOrders() {
        UUID clientId = currentUserProvider.currentUserId().value();
        return ResponseEntity.ok(
                listClientStockOrdersUseCase.execute(new ListClientStockOrdersUseCase.Request(clientId))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN') or @orderAccess.isStockOrderOwner(#id, authentication)")
    public ResponseEntity<Void> updateStockOrder(@PathVariable UUID id, @RequestBody UpdateStockOrderDto order) {
        updateStockOrderUseCase.execute(new UpdateStockOrderUseCase.Request(id, order));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStockOrder(@PathVariable UUID id) {
        deleteStockOrderUseCase.execute(new DeleteStockOrderUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
