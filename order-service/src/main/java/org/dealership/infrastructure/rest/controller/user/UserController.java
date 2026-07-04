package org.dealership.infrastructure.rest.controller.user;

import org.dealership.application.port.in.user.*;
import org.dealership.application.port.in.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserUseCase getUserUseCase,
            ListUsersUseCase listUsersUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateUserUseCase.Response> createUser(
            @RequestBody CreateUserUseCase.Request request) {
        CreateUserUseCase.Response response = createUserUseCase.execute(request);
        return ResponseEntity.created(URI.create("/api/users/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderAccess.isSelf(#id, authentication)")
    public ResponseEntity<GetUserUseCase.Response> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserUseCase.execute(new GetUserUseCase.Request(id)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListUsersUseCase.Response> listUsers() {
        return ResponseEntity.ok(listUsersUseCase.execute(new ListUsersUseCase.Request()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderAccess.isSelf(#id, authentication)")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id, @RequestBody UserDto user) {
        updateUserUseCase.execute(new UpdateUserUseCase.Request(user));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.execute(new DeleteUserUseCase.Request(id));
        return ResponseEntity.noContent().build();
    }
}
