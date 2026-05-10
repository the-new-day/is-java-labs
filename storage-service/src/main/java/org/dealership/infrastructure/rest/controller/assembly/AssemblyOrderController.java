package org.dealership.infrastructure.rest.controller.assembly;

import org.dealership.application.port.in.assembly.CreateAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.DeleteAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.GetAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.ListAssemblyOrdersUseCase;
import org.dealership.application.port.in.assembly.UpdateAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.dto.NewAssemblyOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/assembly-orders")
@PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
public class AssemblyOrderController {

    private final CreateAssemblyOrderUseCase createAssemblyOrderUseCase;
    private final GetAssemblyOrderUseCase getAssemblyOrderUseCase;
    private final ListAssemblyOrdersUseCase listAssemblyOrdersUseCase;
    private final UpdateAssemblyOrderUseCase updateAssemblyOrderUseCase;
    private final DeleteAssemblyOrderUseCase deleteAssemblyOrderUseCase;

    public AssemblyOrderController(
            CreateAssemblyOrderUseCase createAssemblyOrderUseCase,
            GetAssemblyOrderUseCase getAssemblyOrderUseCase,
            ListAssemblyOrdersUseCase listAssemblyOrdersUseCase,
            UpdateAssemblyOrderUseCase updateAssemblyOrderUseCase,
            DeleteAssemblyOrderUseCase deleteAssemblyOrderUseCase
    ) {
        this.createAssemblyOrderUseCase = createAssemblyOrderUseCase;
        this.getAssemblyOrderUseCase = getAssemblyOrderUseCase;
        this.listAssemblyOrdersUseCase = listAssemblyOrdersUseCase;
        this.updateAssemblyOrderUseCase = updateAssemblyOrderUseCase;
        this.deleteAssemblyOrderUseCase = deleteAssemblyOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateAssemblyOrderUseCase.Response> createAssemblyOrder(
            @RequestBody NewAssemblyOrderDto newAssemblyOrder) {
        CreateAssemblyOrderUseCase.Response response =
                createAssemblyOrderUseCase.execute(new CreateAssemblyOrderUseCase.Request(newAssemblyOrder));
        return ResponseEntity.created(URI.create("/api/assembly-orders/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<ListAssemblyOrdersUseCase.Response> listAssemblyOrders() {
        return ResponseEntity.ok(listAssemblyOrdersUseCase.execute(new ListAssemblyOrdersUseCase.Request()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAssemblyOrderUseCase.Response> getAssemblyOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(getAssemblyOrderUseCase.execute(new GetAssemblyOrderUseCase.Request(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAssemblyOrder(
            @PathVariable UUID id,
            @RequestBody NewAssemblyOrderDto assemblyOrder) {
        updateAssemblyOrderUseCase.execute(new UpdateAssemblyOrderUseCase.Request(id, assemblyOrder));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssemblyOrder(@PathVariable UUID id) {
        deleteAssemblyOrderUseCase.execute(new DeleteAssemblyOrderUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
