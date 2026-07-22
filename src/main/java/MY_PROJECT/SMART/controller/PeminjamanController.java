package MY_PROJECT.SMART.controller;

import MY_PROJECT.SMART.model.Peminjaman;
import MY_PROJECT.SMART.security.JwtService;
import MY_PROJECT.SMART.service.PeminjamanService;
import MY_PROJECT.SMART.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/peminjaman")
@RequiredArgsConstructor
public class PeminjamanController {

    private final PeminjamanService peminjamanService;
    private final JwtService jwtService;
    private final UserService userService;

    // ==================== POST ====================

    // POST buat peminjaman baru (sesuai form)
    @PostMapping
    public ResponseEntity<?> createPeminjaman(
            @RequestParam Long ruanganId,
            @RequestParam String namaOrganisasi,
            @RequestParam String penanggungJawab,
            @RequestParam String tanggal,
            @RequestParam String jamMulai,
            @RequestParam String jamSelesai,
            @RequestParam String perihal,
            @RequestParam(required = false) String keterangan,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Ekstrak username dari token
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }

            // Parse tanggal dan jam
            LocalDate tanggalParsed = LocalDate.parse(tanggal);
            LocalTime jamMulaiParsed = LocalTime.parse(jamMulai);
            LocalTime jamSelesaiParsed = LocalTime.parse(jamSelesai);

            // Validasi tanggal tidak boleh sebelum hari ini
            if (tanggalParsed.isBefore(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"Tanggal tidak boleh sebelum hari ini\"}");
            }

            Peminjaman peminjaman = peminjamanService.createPeminjaman(
                    username,
                    ruanganId,
                    namaOrganisasi,
                    penanggungJawab,
                    tanggalParsed,
                    jamMulaiParsed,
                    jamSelesaiParsed,
                    perihal,
                    keterangan
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(peminjaman);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Format tanggal salah! Gunakan: 2026-07-15, jam: 10:00\"}");
        }
    }

    // ==================== GET ====================

    // GET semua peminjaman (admin)
    @GetMapping
    public ResponseEntity<?> getAllPeminjaman(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            // CEK ROLE ADMIN!
            if (!userService.isAdmin(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("{\"error\": \"Hanya admin yang bisa melihat semua peminjaman\"}");
            }

            return ResponseEntity.ok(peminjamanService.getAllPeminjaman());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET peminjaman saya (riwayat)
    @GetMapping("/saya")
    public ResponseEntity<?> getPeminjamanSaya(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(peminjamanService.getPeminjamanByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // GET peminjaman by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPeminjamanById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            return ResponseEntity.ok(peminjamanService.getPeminjamanById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ==================== PUT ====================

    // PUT update status peminjaman (admin)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatusPeminjaman(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }

            //Cek Role Admin!
            if (!userService.isAdmin(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("{\"error\": \"Hanya admin yang bisa approve/reject\"}");
            }

            Peminjaman peminjaman = peminjamanService.updateStatusPeminjaman(id, status);
            return ResponseEntity.ok(peminjaman);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ==================== DELETE ====================

    // DELETE peminjaman (batal)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeminjaman(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!jwtService.validateToken(token, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\": \"Token tidak valid\"}");
            }
            peminjamanService.deletePeminjaman(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}