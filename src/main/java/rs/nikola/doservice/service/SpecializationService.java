package rs.nikola.doservice.service;


import org.springframework.stereotype.Service;
import rs.nikola.doservice.entity.Specialization;
import rs.nikola.doservice.repository.SpecializationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }


    public List<Specialization> getAllActive() {
        return specializationRepository.findByDeletedAtIsNull();
    }

    public Specialization getById(Long id) {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Specialization not found"));
    }


    public Specialization save(Specialization spec) {
        if (specializationRepository.existsByNameAndDeletedAtIsNull(spec.getName())) {
            throw new IllegalArgumentException("Specialization already exists!");
        }
        return specializationRepository.save(spec);
    }


    public void softDelete(Long id) {
        Specialization spec = getById(id);
        spec.setDeletedAt(LocalDateTime.now());
        specializationRepository.save(spec);
    }


    public Optional<Specialization> findByName(String name) {
        return specializationRepository.findByNameAndDeletedAtIsNull(name);
    }
}
