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
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7); // Hapus "Bearer "
        String username = jwtService.extractUsername(token);
        //valid token
        jwtService.validateToken(token, username);
            return ResponseEntity.ok(userService.getAllUsers());
        }


    // GET user by ID (WAJIB PAKE TOKEN!)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        jwtService.validateToken(token, username);
            return ResponseEntity.ok(userService.getUserById(id));
        }


    // POST buat user baru (WAJIB PAKE TOKEN!)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, @RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        jwtService.validateToken(token, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        }

    // DELETE user (WAJIB PAKE TOKEN!)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Cek token
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        jwtService.validateToken(token, username);
        userService.deleteUser(id);
            return ResponseEntity.noContent().build();

        }
    }