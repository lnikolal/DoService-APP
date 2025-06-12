package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Specialization;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {


    List<Specialization> findByDeletedAtIsNull();


    Optional<Specialization> findByNameAndDeletedAtIsNull(String name);


    boolean existsByNameAndDeletedAtIsNull(String name);


    List<Specialization> findAll();
}
