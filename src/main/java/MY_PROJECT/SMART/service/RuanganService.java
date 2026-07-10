package MY_PROJECT.SMART.service;

import MY_PROJECT.SMART.model.Ruangan;
import MY_PROJECT.SMART.repository.RuanganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuanganService {

    private final RuanganRepository ruanganRepository;

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

    // DELETE ruangan
    public void deleteRuangan(Long id) {
        ruanganRepository.deleteById(id);
    }
}