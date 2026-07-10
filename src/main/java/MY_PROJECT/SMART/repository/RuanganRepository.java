package MY_PROJECT.SMART.repository;

import MY_PROJECT.SMART.model.Ruangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuanganRepository extends JpaRepository<Ruangan, Long> {

    // Cari ruangan berdasarkan zona dan lantai
    List<Ruangan> findByZonaAndLantai(String zona, Integer lantai);

    // Cari ruangan berdasarkan status (Tersedia, Dibooking, Digunakan)
    List<Ruangan> findByStatus(String status);

    // Cari ruangan berdasarkan zona
    List<Ruangan> findByZona(String zona);
}
