package org.dealership.infrastructure.rest.controller.stockorder;

import org.dealership.application.port.in.stockorder.*;
import org.dealership.application.port.in.stockorder.dto.StockOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-orders")
public class StockOrderController {
    private final CreateStockOrderUseCase createStockOrderUseCase;
    private final GetStockOrderUseCase getStockOrderUseCase;
    private final ListStockOrdersUseCase listStockOrdersUseCase;
    private final UpdateStockOrderUseCase updateStockOrderUseCase;
    private final DeleteStockOrderUseCase deleteStockOrderUseCase;

    public StockOrderController(
            CreateStockOrderUseCase createStockOrderUseCase,
            GetStockOrderUseCase getStockOrderUseCase,
            ListStockOrdersUseCase listStockOrdersUseCase,
            UpdateStockOrderUseCase updateStockOrderUseCase,
            DeleteStockOrderUseCase deleteStockOrderUseCase) {
        this.createStockOrderUseCase = createStockOrderUseCase;
        this.getStockOrderUseCase = getStockOrderUseCase;
        this.listStockOrdersUseCase = listStockOrdersUseCase;
        this.updateStockOrderUseCase = updateStockOrderUseCase;
        this.deleteStockOrderUseCase = deleteStockOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateStockOrderUseCase.Response> createStockOrder(
            @RequestBody CreateStockOrderUseCase.Request request
    ) {
        CreateStockOrderUseCase.Response response = createStockOrderUseCase.execute(request);
        return ResponseEntity.created(URI.create("/api/stock-orders/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetStockOrderUseCase.Response> getStockOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(getStockOrderUseCase.execute(new GetStockOrderUseCase.Request(id)));
    }

    @GetMapping
    public ResponseEntity<ListStockOrdersUseCase.Response> listStockOrders() {
        return ResponseEntity.ok(listStockOrdersUseCase.execute(new ListStockOrdersUseCase.Request()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStockOrder(@PathVariable UUID id, @RequestBody StockOrderDto order) {
        updateStockOrderUseCase.execute(new UpdateStockOrderUseCase.Request(order));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockOrder(@PathVariable UUID id) {
        deleteStockOrderUseCase.execute(new DeleteStockOrderUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
