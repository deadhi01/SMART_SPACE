package MY_PROJECT.SMART.controller;

import MY_PROJECT.SMART.model.User;
import MY_PROJECT.SMART.security.JwtService;
import MY_PROJECT.SMART.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    // GET semua user (WAJIB PAKE TOKEN!)
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7); // Hapus "Bearer "
        String username = jwtService.extractUsername(token);

        // Validasi token
        if (!jwtService.validateToken(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Token tidak valid atau kadaluarsa\"}");
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET user by ID (WAJIB PAKE TOKEN!)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (!jwtService.validateToken(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Token tidak valid atau kadaluarsa\"}");
        }

        try {
            // Kita belum ada metode getById di service, jadi kita pake alternatif
            List<User> users = userService.getAllUsers();
            User user = users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // POST buat user baru (WAJIB PAKE TOKEN!)
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (!jwtService.validateToken(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Token tidak valid atau kadaluarsa\"}");
        }

        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // DELETE user (WAJIB PAKE TOKEN!)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (!jwtService.validateToken(token, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Token tidak valid atau kadaluarsa\"}");
        }

        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}