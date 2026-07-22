package MY_PROJECT.SMART.service;

import MY_PROJECT.SMART.model.Peminjaman;
import MY_PROJECT.SMART.model.Ruangan;
import MY_PROJECT.SMART.repository.PeminjamanRepository;
import MY_PROJECT.SMART.repository.RuanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuanganService {

    private final RuanganRepository ruanganRepository;
    private final PeminjamanRepository peminjamanRepository;

    // GET semua ruangan
    public List<Ruangan> getAllRuangan() {
        return ruanganRepository.findAll();
    }

    // GET ruangan by ID
    public Ruangan getRuanganById(Long id) {
        return ruanganRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruangan tidak ditemukan"));
    }

    // GET ruangan by status (Tersedia, Dibooking, Digunakan)
    public List<Ruangan> getRuanganByStatus(String status) {
        return ruanganRepository.findByStatus(status);
    }

    // GET ruangan by zona
    public List<Ruangan> getRuanganByZona(String zona) {
        return ruanganRepository.findByZona(zona);
    }

    // ⭐ TAMBAHKAN METHOD INI! ⭐
    // GET ruangan by zona dan lantai
    public List<Ruangan> getRuanganByZonaAndLantai(String zona, Integer lantai) {
        return ruanganRepository.findByZonaAndLantai(zona, lantai);
    }

    // POST tambah ruangan
    public Ruangan createRuangan(Ruangan ruangan) {
        return ruanganRepository.save(ruangan);
    }

    // GET ruangan by lantai
    public List<Ruangan> getRuanganByLantai(Integer lantai) {
        return ruanganRepository.findByLantai(lantai);
    }

    // PUT update ruangan
    public Ruangan updateRuangan(Long id, Ruangan ruanganBaru) {
        Ruangan ruangan = getRuanganById(id);
        ruangan.setNama(ruanganBaru.getNama());
        ruangan.setZona(ruanganBaru.getZona());
        ruangan.setLantai(ruanganBaru.getLantai());
        ruangan.setStatus(ruanganBaru.getStatus());
        ruangan.setFasilitas(ruanganBaru.getFasilitas());
        ruangan.setKapasitas(ruanganBaru.getKapasitas());
        return ruanganRepository.save(ruangan);
    }

    // Cek ketersediaan ruangan
    public boolean cekKetersediaan(Long ruanganId, LocalDate tanggal, LocalTime jamMulai, LocalTime jamSelesai) {
        // Cek apakah ada peminjaman yang APPROVED atau PENDING di waktu yang sama
        List<Peminjaman> peminjamanList = peminjamanRepository.findByRuanganIdAndTanggal(ruanganId, tanggal);

        for (Peminjaman p : peminjamanList) {
            if (p.getStatus().equals("APPROVED") || p.getStatus().equals("PENDING")) {
                boolean isOverlap = !(jamSelesai.isBefore(p.getJamMulai()) || jamMulai.isAfter(p.getJamSelesai()));
                if (isOverlap) {
                    return false; // Ruangan tidak tersedia
                }
            }
        }
        return true; // Ruangan tersedia
    }

    // DELETE ruangan
    public void deleteRuangan(Long id) {
        ruanganRepository.deleteById(id);
    }
}