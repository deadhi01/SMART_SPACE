package MY_PROJECT.SMART.controller;

import MY_PROJECT.SMART.model.Ruangan;
import MY_PROJECT.SMART.security.JwtService;
import MY_PROJECT.SMART.service.RuanganService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/ruangan")
@RequiredArgsConstructor
public class RuanganController {

    private final RuanganService ruanganService;
    private final JwtService jwtService;

    // ==================== GET ====================

    // GET semua ruangan (WAJIB TOKEN)
    @GetMapping
    public ResponseEntity<?> getAllRuangan(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(ruanganService.getAllRuangan());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Token tidak valid\"}");
        }
    }

    // GET ruangan by ID (WAJIB TOKEN)
    @GetMapping("/{id}")
    public ResponseEntity<?> getRuanganById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(ruanganService.getRuanganById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET ruangan by status (WAJIB TOKEN)
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getRuanganByStatus(@PathVariable String status, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(ruanganService.getRuanganByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET ruangan by zona (WAJIB TOKEN)
    @GetMapping("/zona/{zona}")
    public ResponseEntity<?> getRuanganByZona(@PathVariable String zona, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(ruanganService.getRuanganByZona(zona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET ruangan by zona dan lantai (WAJIB TOKEN)
    @GetMapping("/zona/{zona}/lantai/{lantai}")
    public ResponseEntity<?> getRuanganByZonaAndLantai(
            @PathVariable String zona,
            @PathVariable Integer lantai,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(ruanganService.getRuanganByZonaAndLantai(zona, lantai));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ⭐ GET ruangan by lantai (WAJIB TOKEN) ⭐
    @GetMapping("/lantai/{lantai}")
    public ResponseEntity<?> getRuanganByLantai(
            @PathVariable Integer lantai,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(ruanganService.getRuanganByLantai(lantai));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ==================== POST ====================

    // POST tambah ruangan (WAJIB TOKEN)
    @PostMapping
    public ResponseEntity<?> createRuangan(@RequestBody Ruangan ruangan, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            Ruangan newRuangan = ruanganService.createRuangan(ruangan);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRuangan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ==================== PUT ====================

    // PUT update ruangan (WAJIB TOKEN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRuangan(@PathVariable Long id, @RequestBody Ruangan ruangan, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            Ruangan updatedRuangan = ruanganService.updateRuangan(id, ruangan);
            return ResponseEntity.ok(updatedRuangan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ==================== DELETE ====================

    // DELETE ruangan (WAJIB TOKEN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRuangan(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            ruanganService.deleteRuangan(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET cek ketersediaan ruangan (WAJIB TOKEN)
    @GetMapping("/{id}/ketersediaan")
    public ResponseEntity<?> cekKetersediaan(
            @PathVariable Long id,
            @RequestParam String tanggal,
            @RequestParam String jamMulai,
            @RequestParam String jamSelesai,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }

            // Parse tanggal & jam
            LocalDate tanggalParsed = LocalDate.parse(tanggal);
            LocalTime jamMulaiParsed = LocalTime.parse(jamMulai);
            LocalTime jamSelesaiParsed = LocalTime.parse(jamSelesai);

            // Cek apakah ruangan tersedia
            boolean tersedia = ruanganService.cekKetersediaan(id, tanggalParsed, jamMulaiParsed, jamSelesaiParsed);

            Map<String, Object> response = new HashMap<>();
            response.put("ruanganId", id);
            response.put("tanggal", tanggal);
            response.put("jamMulai", jamMulai);
            response.put("jamSelesai", jamSelesai);
            response.put("tersedia", tersedia);
            response.put("message", tersedia ? "Ruangan tersedia!" : "Ruangan sudah dipinjam di jam tersebut!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}