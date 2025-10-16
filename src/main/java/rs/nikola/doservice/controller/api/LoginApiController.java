package rs.nikola.doservice.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class LoginApiController {

    // Ovaj endpoint radi “proveru” da li je korisnik validan
    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Login OK, možeš odmah vratiti npr. osnovne podatke o korisniku, ili poruku za frontend
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful!",
                    "username", authentication.getName(),
                    "role", authentication.getAuthorities().iterator().next().getAuthority()
            ));
        } else {
            // Ovo je fallback, u praksi bi security filter odbio zahtev pre ovde
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
