package MY_PROJECT.SMART.service;

import MY_PROJECT.SMART.model.User;
import MY_PROJECT.SMART.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // GET semua user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET user by ID ⬅️ TAMBAHKAN INI!
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    // GET user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    //Cek apakah user adalah admin
    @SuppressWarnings("UnusedReturnValue")
    public boolean isAdmin(String username) {
        User user = getUserByUsername(username);
        return "ADMIN".equals(user.getRole());
    }

    // REGISTER
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username sudah terdaftar!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("MAHASISWA");
        }
        return userRepository.save(user);
    }

    // LOGIN
    public User loginUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username tidak ditemukan"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Password salah!");
        }
        return user;
    }

    // CREATE
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username sudah terdaftar!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("MAHASISWA");
        }
        return userRepository.save(user);
    }

    // DELETE
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User tidak ditemukan");
        }
        userRepository.deleteById(id);
    }
}