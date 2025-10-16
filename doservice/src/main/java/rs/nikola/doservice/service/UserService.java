package rs.nikola.doservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.nikola.doservice.dto.UserDto;
import rs.nikola.doservice.entity.Role;
import rs.nikola.doservice.entity.Technician;
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

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllActive() {
        return userRepository.findByDeletedAtIsNull();
    }


    public Page<User> getAllActive(Pageable pageable) {
        return userRepository.findByDeletedAtIsNull(pageable);
    }


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


    public User saveFromDto(UserDto dto) {
        User user;
        boolean isNew = (dto.getId() == null);

        if (isNew) {
            user = new User();
            user.setCreatedAt(LocalDateTime.now());
        } else {
            user = getById(dto.getId());
        }
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));
            user.setRole(role);
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        user.setUpdatedAt(LocalDateTime.now());
        user.setDeletedAt(null);

        if (isNew) {
            if (usernameExists(user.getUsername()))
                throw new IllegalArgumentException("Username already exists!");
            if (emailExists(user.getEmail()))
                throw new IllegalArgumentException("Email already exists!");
        } else {

            if (userRepository.existsByUsernameAndDeletedAtIsNull(dto.getUsername()) && !user.getUsername().equals(dto.getUsername())) {
                throw new IllegalArgumentException("Username already exists!");
            }
            if (userRepository.existsByEmailAndDeletedAtIsNull(dto.getEmail()) && !user.getEmail().equals(dto.getEmail())) {
                throw new IllegalArgumentException("Email already exists!");
            }
        }
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
