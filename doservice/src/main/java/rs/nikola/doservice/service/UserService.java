package rs.nikola.doservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.nikola.doservice.entity.Role;
import rs.nikola.doservice.entity.User;
import rs.nikola.doservice.repository.RoleRepository;
import rs.nikola.doservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // === LISTANJE I PRETRAGE ===

    // Listaj sve aktivne (neobrisane) korisnike
    public List<User> getAllActive() {
        return userRepository.findByDeletedAtIsNull();
    }

    // Paginacija aktivnih korisnika
    public Page<User> getAllActive(Pageable pageable) {
        return userRepository.findByDeletedAtIsNull(pageable);
    }

    // Dohvati korisnika po ID-u (samo ako nije obrisan)
    public User getById(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameAndDeletedAtIsNull(username);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email);
    }


    public List<User> getByRole(Role role) {
        return userRepository.findByRoleAndDeletedAtIsNull(role);
    }


    public boolean usernameExists(String username) {
        return userRepository.existsByUsernameAndDeletedAtIsNull(username);
    }


    public boolean emailExists(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNull(email);
    }


    public User save(User user) {
        if (usernameExists(user.getUsername()))
            throw new IllegalArgumentException("Username already exists!");
        if (emailExists(user.getEmail()))
            throw new IllegalArgumentException("Email already exists!");


        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setDeletedAt(null);

        return userRepository.save(user);
    }




    public User update(Long id, User data) {
        User existing = getById(id);
        existing.setUsername(data.getUsername());
        existing.setEmail(data.getEmail());
        existing.setRole(data.getRole());

        existing.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existing);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void softDelete(Long id) {
        User user = getById(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }


    public User findOptionalById(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()-> new NoSuchElementException("User not found"));
    }
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public Role getRoleByName(String role) {
        return roleRepository.findByName(role).orElseThrow(() -> new NoSuchElementException("Role not found: " + role));
    }
}
