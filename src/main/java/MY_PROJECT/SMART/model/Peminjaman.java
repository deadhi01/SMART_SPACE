package MY_PROJECT.SMART.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "peminjaman")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Yang pinjam (dari token)

    @ManyToOne
    @JoinColumn(name = "ruangan_id", nullable = false)
    private Ruangan ruangan;  // Ruangan yang dipilih

    @Column(nullable = false)
    private String namaOrganisasi;  // Nama organisasi peminjam

    @Column(nullable = false)
    private String penanggungJawab;  // Nama penanggung jawab

    @Column(nullable = false)
    private LocalDate tanggal;  // Tanggal kegiatan

    @Column(nullable = false)
    private LocalTime jamMulai;  // Jam mulai

    @Column(nullable = false)
    private LocalTime jamSelesai;  // Jam selesai

    @Column(nullable = false)
    private String perihal;  // Perihal/kegiatan

    @Column(nullable = false)
    private String status;  // "PENDING", "APPROVED", "REJECTED", "CANCELLED"

    private String keterangan;  // Catatan tambahan (opsional)
}