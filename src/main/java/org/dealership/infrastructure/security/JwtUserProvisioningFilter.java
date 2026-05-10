package org.dealership.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dealership.application.port.in.user.CreateUserUseCase;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.model.id.UserId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtUserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final CreateUserUseCase createUserUseCase;

    public JwtUserProvisioningFilter(UserRepository userRepository, CreateUserUseCase createUserUseCase) {
        this.userRepository = userRepository;
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth
                && jwtAuth.getPrincipal() instanceof Jwt jwt
                && jwt.getSubject() != null) {
            UUID sub = parseUuidOrNull(jwt.getSubject());
            if (sub != null && userRepository.findById(new UserId(sub)).isEmpty()) {
                String displayName = resolveDisplayName(jwt);
                createUserUseCase.execute(new CreateUserUseCase.Request(sub, displayName));
            }
        }
        filterChain.doFilter(request, response);
    }

    private static UUID parseUuidOrNull(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static String resolveDisplayName(Jwt jwt) {
        String name = jwt.getClaimAsString("name");
        if (name != null && !name.isBlank()) {
            return name;
        }
        String preferred = jwt.getClaimAsString("preferred_username");
        if (preferred != null && !preferred.isBlank()) {
            return preferred;
        }
        return jwt.getSubject();
    }
}
