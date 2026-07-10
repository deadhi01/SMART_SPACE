package MY_PROJECT.SMART.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ruangan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ruangan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String zona;

    @Column(nullable = false)
    private Integer lantai;

    @Column(nullable = false)
    private String status; // "Tersedia", "Dibooking", "Digunakan"

    private String fasilitas;

    @Column(nullable = false)
    private Integer kapasitas;
}
