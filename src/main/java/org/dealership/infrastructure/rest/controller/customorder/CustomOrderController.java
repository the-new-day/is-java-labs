package org.dealership.infrastructure.rest.controller.customorder;

import org.dealership.application.port.in.customorder.*;
import org.dealership.application.port.in.customorder.dto.CustomOrderDto;
import org.springframework.http.ResponseEntity;
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

    public CustomOrderController(
            CreateCustomOrderUseCase createCustomOrderUseCase,
            GetCustomOrderUseCase getCustomOrderUseCase,
            ListCustomOrdersUseCase listCustomOrdersUseCase,
            UpdateCustomOrderUseCase updateCustomOrderUseCase,
            DeleteCustomOrderUseCase deleteCustomOrderUseCase) {
        this.createCustomOrderUseCase = createCustomOrderUseCase;
        this.getCustomOrderUseCase = getCustomOrderUseCase;
        this.listCustomOrdersUseCase = listCustomOrdersUseCase;
        this.updateCustomOrderUseCase = updateCustomOrderUseCase;
        this.deleteCustomOrderUseCase = deleteCustomOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateCustomOrderUseCase.Response> createCustomOrder(
            @RequestBody CreateCustomOrderUseCase.Request request
    ) {
        CreateCustomOrderUseCase.Response response = createCustomOrderUseCase.execute(request);
        return ResponseEntity.created(URI.create("/api/custom-orders/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomOrderUseCase.Response> getCustomOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(getCustomOrderUseCase.execute(new GetCustomOrderUseCase.Request(id)));
    }

    @GetMapping
    public ResponseEntity<ListCustomOrdersUseCase.Response> listCustomOrders() {
        return ResponseEntity.ok(listCustomOrdersUseCase.execute(new ListCustomOrdersUseCase.Request()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomOrder(@PathVariable UUID id, @RequestBody CustomOrderDto order) {
        updateCustomOrderUseCase.execute(new UpdateCustomOrderUseCase.Request(order));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomOrder(@PathVariable UUID id) {
        deleteCustomOrderUseCase.execute(new DeleteCustomOrderUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
