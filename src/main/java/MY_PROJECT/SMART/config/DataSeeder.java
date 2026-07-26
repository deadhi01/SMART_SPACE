package MY_PROJECT.SMART.config;

import MY_PROJECT.SMART.model.Ruangan;
import MY_PROJECT.SMART.model.User;
import MY_PROJECT.SMART.repository.RuanganRepository;
import MY_PROJECT.SMART.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RuanganRepository ruanganRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        // =========================================================
        // 1. SEEDING USER (Admin, Mahasiswa, Organisasi)
        // =========================================================
        if (userRepository.count() == 0) {
            System.out.println("🌱 Seeding data user...");

            // 1. Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(admin);

            //2. Mahasiswa (tidak bisa booking)
            User mahasiswa = new User();
            mahasiswa.setUsername("budi123");
            mahasiswa.setPassword(passwordEncoder.encode("rahasia123"));
            mahasiswa.setRole("MAHASISWA");
            mahasiswa.setCreatedAt(LocalDateTime.now());
            mahasiswa.setUpdatedAt(LocalDateTime.now());
            userRepository.save(mahasiswa);

            //3. Organisasi (Bisa Booking)
            //himafor
            User organisasi1 = new User();
            organisasi1.setUsername("himafor");
            organisasi1.setPassword(passwordEncoder.encode("himafor123"));
            organisasi1.setRole("ORGANISASI");
            organisasi1.setCreatedAt(LocalDateTime.now());
            organisasi1.setUpdatedAt(LocalDateTime.now());
            userRepository.save(organisasi1);

            //himatikro
            User organisasi2 = new User();
            organisasi2.setUsername("himatikro");
            organisasi2.setPassword(passwordEncoder.encode("himatikro123"));
            organisasi2.setRole("ORGANISASI");
            organisasi2.setCreatedAt(LocalDateTime.now());
            organisasi2.setUpdatedAt(LocalDateTime.now());
            userRepository.save(organisasi2);

            //hmtm
            User organisasi3 = new User();
            organisasi3.setUsername("hmtm");
            organisasi3.setPassword(passwordEncoder.encode("hmtm123"));
            organisasi3.setRole("ORGANISASI");
            organisasi3.setCreatedAt(LocalDateTime.now());
            organisasi3.setUpdatedAt(LocalDateTime.now());
            userRepository.save(organisasi3);

            //hmti
            User organisasi4 = new User();
            organisasi4.setUsername("hmti");
            organisasi4.setPassword(passwordEncoder.encode("hmti123"));
            organisasi4.setRole("ORGANISASI");
            organisasi4.setCreatedAt(LocalDateTime.now());
            organisasi4.setUpdatedAt(LocalDateTime.now());
            userRepository.save(organisasi4);

            //hma
            User organisasi5 = new User();
            organisasi5.setUsername("hma");
            organisasi5.setPassword(passwordEncoder.encode("hma123"));
            organisasi5.setRole("ORGANISASI");
            organisasi5.setCreatedAt(LocalDateTime.now());
            organisasi5.setUpdatedAt(LocalDateTime.now());
            userRepository.save(organisasi5);

            //hmrs
            User organisasi6 = new User();
            organisasi6.setUsername("hmrs");
            organisasi6.setPassword(passwordEncoder.encode("hmrs123"));
            organisasi6.setRole("ORGANISASI");
            organisasi6.setCreatedAt(LocalDateTime.now());
            organisasi6.setUpdatedAt(LocalDateTime.now());
            userRepository.save(organisasi6);

        System.out.println("✅ User berhasil di-seed: admin (ADMIN), budi123 (MAHASISWA), himafor (ORGANISASI), himatikro (ORGANISASI), hmtm (ORGANISASI), hmti (ORGANISASI), hma (ORGANISASI), hmrs (ORGANISASI)");
} else {
        System.out.println("✅ Data user sudah ada, skip seeder.");
    }

        // =========================================================
        // 2. SEEDING RUANGAN (Zona B Lantai 2)
        // =========================================================

        if (ruanganRepository.count() > 0) {
            System.out.println("✅ Data ruangan sudah ada, skip seeder.");
            return;
        }

        System.out.println("🌱 Seeding data ruangan Zona B Lantai 2...");

        List<Ruangan> ruanganList = Arrays.asList(
                new Ruangan(null, "B.201 Laboratorium Bahasa Multimedia & Virtual Reality", "Zona B", 2, "Tersedia", "Virtual Reality, Audio System, 40 PC", 40),
                new Ruangan(null, "B.202 Lab. Manajemen Database (Kesehatan & Medis)", "Zona B", 2, "Dibooking", "Database Server, 30 PC, Medical Software", 30),
                new Ruangan(null, "B.203 Algoritma & Pemrograman", "Zona B", 2, "Tersedia", "40 PC, Projector, Whiteboard", 40),
                new Ruangan(null, "B.207 Ruang Administrasi Lab. Komputer", "Zona B", 2, "Digunakan", "5 PC, Printer, Filing Cabinet", 10),
                new Ruangan(null, "B.208 Lab. Arsitektur & Jaringan Komputer", "Zona B", 2, "Tersedia", "30 PC, Cisco Router, Switch", 30),
                new Ruangan(null, "B.209 Lab. Sistem Cerdas & Visi", "Zona B", 2, "Dibooking", "AI Workstation, Kamera, 25 PC", 25),
                new Ruangan(null, "B.210 Lab. Pemodelan Komputasi Terapan & Rekayasa Perangkat", "Zona B", 2, "Tersedia", "35 PC, 3D Printer, Software Engineering Tools", 35)
        );

        ruanganRepository.saveAll(ruanganList);
        System.out.println("✅ " + ruanganList.size() + " ruangan Zona B Lantai 2 berhasil di-seed!");
    }
}