package MY_PROJECT.SMART.controller;

import MY_PROJECT.SMART.model.User;
import MY_PROJECT.SMART.security.AuthRequest;
import MY_PROJECT.SMART.security.AuthResponse;
import MY_PROJECT.SMART.security.JwtService;
import MY_PROJECT.SMART.service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    // REGISTER (Daftar Akun Baru)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }

    }

    // LOGIN (Masuk & Dapat Token)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            User user = userService.loginUser(request.getUsername(), request.getPassword());
            String token = jwtService.generateToken(user.getUsername());

            // Kirim token + username + role
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("role", user.getRole());  // ⭐ INI YANG DITAMBAH

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            // Token tidak perlu divalidasi atau di-blacklist
            // Cukup return sukses, mobile yang hapus token
            return ResponseEntity.ok("{\"message\": \"Logout berhasil\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}