package MY_PROJECT.SMART.controller;

import MY_PROJECT.SMART.model.User;
import MY_PROJECT.SMART.security.AuthRequest;
import MY_PROJECT.SMART.security.AuthResponse;
import MY_PROJECT.SMART.security.JwtService;
import MY_PROJECT.SMART.service.UserService;
import lombok.RequiredArgsConstructor;
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
            // Validasi username & password
            User user = userService.loginUser(request.getUsername(), request.getPassword());

            // Generate JWT token
            String token = jwtService.generateToken(user.getUsername());

            // Kirim token ke client
            return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}