package MY_PROJECT.SMART.service;

import MY_PROJECT.SMART.model.Peminjaman;
import MY_PROJECT.SMART.model.Ruangan;
import MY_PROJECT.SMART.model.User;
import MY_PROJECT.SMART.repository.PeminjamanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeminjamanService {

    private final PeminjamanRepository peminjamanRepository;
    private final RuanganService ruanganService;
    private final UserService userService;

    // GET semua peminjaman (admin)
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanRepository.findAll();
    }

    // GET peminjaman by user (riwayat saya)
    public List<Peminjaman> getPeminjamanByUser(User user) {
        return peminjamanRepository.findByUser(user);
    }

    // GET peminjaman by username
    public List<Peminjaman> getPeminjamanByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return peminjamanRepository.findByUser(user);
    }

    // GET peminjaman by ID
    public Peminjaman getPeminjamanById(Long id) {
        return peminjamanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peminjaman tidak ditemukan"));
    }

    // POST buat peminjaman baru (sesuai form)
    public Peminjaman createPeminjaman(
            String username,
            Long ruanganId,
            String namaOrganisasi,
            String penanggungJawab,
            LocalDate tanggal,
            LocalTime jamMulai,
            LocalTime jamSelesai,
            String perihal,
            String keterangan) {

        // Cek user
        User user = userService.getUserByUsername(username);

        // Cek ruangan
        Ruangan ruangan = ruanganService.getRuanganById(ruanganId);

        // Validasi jam
        if (jamMulai.isAfter(jamSelesai) || jamMulai.equals(jamSelesai)) {
            throw new RuntimeException("Jam mulai harus sebelum jam selesai!");
        }

        // Cek apakah ruangan sudah dipinjam di tanggal dan jam yang sama
        List<Peminjaman> existingBookings = peminjamanRepository.findByRuanganIdAndTanggal(ruanganId, tanggal);
        for (Peminjaman p : existingBookings) {
            if (p.getStatus().equals("APPROVED") || p.getStatus().equals("PENDING")) {
                boolean isOverlap = !(jamSelesai.isBefore(p.getJamMulai()) || jamMulai.isAfter(p.getJamSelesai()));
                if (isOverlap) {
                    throw new RuntimeException("Ruangan sudah dipinjam di jam tersebut!");
                }
            }
        }

        Peminjaman peminjaman = new Peminjaman();
        peminjaman.setUser(user);
        peminjaman.setRuangan(ruangan);
        peminjaman.setNamaOrganisasi(namaOrganisasi);
        peminjaman.setPenanggungJawab(penanggungJawab);
        peminjaman.setTanggal(tanggal);
        peminjaman.setJamMulai(jamMulai);
        peminjaman.setJamSelesai(jamSelesai);
        peminjaman.setPerihal(perihal);
        peminjaman.setStatus("PENDING");
        peminjaman.setKeterangan(keterangan);

        return peminjamanRepository.save(peminjaman);
    }

    // PUT update status peminjaman (admin approve/reject)
    public Peminjaman updateStatusPeminjaman(Long id, String status) {
        Peminjaman peminjaman = getPeminjamanById(id);

        if (!status.equals("APPROVED") && !status.equals("REJECTED") && !status.equals("CANCELLED")) {
            throw new RuntimeException("Status tidak valid! Gunakan: APPROVED, REJECTED, atau CANCELLED");
        }

        peminjaman.setStatus(status);
        return peminjamanRepository.save(peminjaman);
    }

    // DELETE peminjaman (batal)
    public void deletePeminjaman(Long id) {
        Peminjaman peminjaman = getPeminjamanById(id);
        if (!peminjaman.getStatus().equals("PENDING")) {
            throw new RuntimeException("Hanya peminjaman dengan status PENDING yang bisa dibatalkan!");
        }
        peminjamanRepository.deleteById(id);
    }
}