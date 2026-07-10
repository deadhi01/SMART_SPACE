package MY_PROJECT.SMART.repository;

import MY_PROJECT.SMART.model.Peminjaman;
import MY_PROJECT.SMART.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {

    List<Peminjaman> findByUser(User user);

    List<Peminjaman> findByStatus(String status);

    List<Peminjaman> findByRuanganId(Long ruanganId);

    // Cari peminjaman berdasarkan ruangan ID dan tanggal
    List<Peminjaman> findByRuanganIdAndTanggal(Long ruanganId, LocalDate tanggal);
}