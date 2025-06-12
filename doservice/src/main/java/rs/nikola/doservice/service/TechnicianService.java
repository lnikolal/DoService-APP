package rs.nikola.doservice.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.nikola.doservice.dto.TechnicianDto;
import rs.nikola.doservice.dto.TechnicianUserDto;
import rs.nikola.doservice.entity.*;
import rs.nikola.doservice.repository.TechnicianRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final UserService userService;
    private final SpecializationService specializationService;
    private final LocationService locationService;
    private final FileStorageService fileStorageService;
    public TechnicianService(TechnicianRepository technicianRepository, UserService userService, SpecializationService specializationService, LocationService locationService, FileStorageService fileStorageService) {
        this.specializationService = specializationService;
        this.locationService = locationService;
        this.technicianRepository = technicianRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    // List all active technicians
    public List<Technician> getAllActive() {
        return technicianRepository.findByDeletedAtIsNull();
    }

    // Paginated listing of all active technicians
    public Page<Technician> getAllActive(Pageable pageable) {
        return technicianRepository.findByDeletedAtIsNull(pageable);
    }

    // Get by ID (only if not deleted)
    public Technician getById(Long id) {
        return technicianRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Technician not found"));
    }

    // Search by first or last name (paginated, only active)
    public Page<Technician> searchByName(String query, Pageable pageable) {
        return technicianRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndDeletedAtIsNull(query, query, pageable);
    }

    // Filter by status
    public List<Technician> getByStatus(TechnicianStatus status) {
        return technicianRepository.findByStatusAndDeletedAtIsNull(status);
    }

    // Filter by location
    public List<Technician> getByLocation(Location location) {
        return technicianRepository.findByLocationAndDeletedAtIsNull(location);
    }

    // Filter by specialization
    public List<Technician> getBySpecialization(Specialization specialization) {
        return technicianRepository.findBySpecializationAndDeletedAtIsNull(specialization);
    }

    // Filter by status, location, specialization (paginated)
    public Page<Technician> filter(TechnicianStatus status, Location location, Specialization specialization, Pageable pageable) {
        return technicianRepository
                .findByStatusAndLocationAndSpecializationAndDeletedAtIsNull(status, location, specialization, pageable);
    }

    // Create new technician
    public Technician save(Technician technician) {

        if (emailExists(technician.getEmail())) {
            throw new IllegalArgumentException("Technician with this email already exists!");
        }



        technician.setCreatedAt(LocalDateTime.now());
        technician.setDeletedAt(null);
        return technicianRepository.save(technician);
    }

    // Update existing technician
    public Technician update(Long id, Technician data) {
        Technician existing = getById(id);
        existing.setFirstName(data.getFirstName());
        existing.setLastName(data.getLastName());
        existing.setEmail(data.getEmail());
        existing.setPhone(data.getPhone());
        existing.setStatus(data.getStatus());
        existing.setSpecialization(data.getSpecialization());
        existing.setLocation(data.getLocation());
        existing.setEmploymentDate(data.getEmploymentDate());
        existing.setUpdatedAt(LocalDateTime.now());
        return technicianRepository.save(existing);
    }


    public void softDelete(Long id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Technician not found for user id: " + id));
        technician.setDeletedAt(LocalDateTime.now());
        technicianRepository.save(technician);

        if (technician.getUser() != null) {
            User user = technician.getUser();

            userService.softDelete(user.getId());
        }
    }


    public boolean emailExists(String email) {
        return technicianRepository.existsByEmailAndDeletedAtIsNull(email);
    }


    public Optional<Technician> findByEmail(String email) {
        return technicianRepository.findByEmailAndDeletedAtIsNull(email);
    }

    public Technician getTechnicianByUserId(Long userId) {
        return technicianRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("Technician not found for user id: " + userId));
    }

    public Technician getTechnicianbyUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        return getTechnicianByUserId(user.get().getId());
    }

    @Transactional
    public Technician createWithUser(TechnicianUserDto dto) {



        User user = new User();
        if (userService.usernameExists(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists in user table!");
        }
        if (userService.emailExists(dto.getUserEmail())){
            throw new IllegalArgumentException("Email already exists in user table!");
        }
        if (emailExists(dto.getUserEmail())) {
            throw new IllegalArgumentException("Email already exists in technician table!");
        }



        user.setUsername(dto.getUsername());
        user.setEmail(dto.getUserEmail());
       // System.out.println("-----------------------"+dto.getPassword());
        user.setPasswordHash(dto.getPassword());
       // System.out.println("-----------------------"+userService.encodePassword(dto.getPassword()));
        user.setRole(userService.getRoleByName("TECHNICIAN"));
        userService.save(user);


        System.out.println("sacuvao sam user-a");
        Technician tech = new Technician();
        if (dto.getProfileImageFile() != null && !dto.getProfileImageFile().isEmpty()) {
            String imageUrl = fileStorageService.store(dto.getProfileImageFile());
            tech.setProfileImageUrl(imageUrl);
        }
        tech.setFirstName(dto.getFirstName());
        tech.setLastName(dto.getLastName());
        tech.setEmail(dto.getTechnicianEmail());
        tech.setPhone(dto.getPhone());
        tech.setStatus(TechnicianStatus.valueOf(dto.getStatus()));
        tech.setLocation(locationService.getById(dto.getLocationId()));
        tech.setSpecialization(specializationService.getById(dto.getSpecializationId()));
        tech.setEmploymentDate(dto.getEmploymentDate());

        System.out.println(dto.getProfileImageUrl());
        tech.setUser(user);

        return technicianRepository.save(tech);
    }
    public void editTechnician(TechnicianDto dto) {
        Technician tech = technicianRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Technician not found!"));



        tech.setFirstName(dto.getFirstName());
        tech.setLastName(dto.getLastName());
        tech.setEmail(dto.getEmail());
        tech.setPhone(dto.getPhone());
        tech.setStatus(TechnicianStatus.valueOf(dto.getStatus()));
        tech.setLocation(locationService.getById(dto.getLocationId()));
        tech.setSpecialization(specializationService.getById(dto.getSpecializationId()));


        if (dto.getEmploymentDate() != null) {
            tech.setEmploymentDate(dto.getEmploymentDate());
        }
        if (dto.getProfileImageFile() != null && !dto.getProfileImageFile().isEmpty()) {
            String imageUrl = fileStorageService.store(dto.getProfileImageFile());
            tech.setProfileImageUrl(imageUrl);
        }

        technicianRepository.save(tech);
    }



        public void setStatus(Long id, String status) {
            Technician technician = technicianRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Technician not found!"));
            technician.setStatus(TechnicianStatus.valueOf(status));
            technicianRepository.save(technician);
        }

}
