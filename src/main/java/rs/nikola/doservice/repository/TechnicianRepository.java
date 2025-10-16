package rs.nikola.doservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.entity.Location;
import rs.nikola.doservice.entity.Specialization;
import rs.nikola.doservice.entity.TechnicianStatus;

import java.util.List;
import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {


    List<Technician> findByDeletedAtIsNull();


    List<Technician> findByStatusAndDeletedAtIsNull(TechnicianStatus status);

    Optional<Technician>  findByIdAndDeletedAtIsNull(Long id);


    List<Technician> findByLocationAndDeletedAtIsNull(Location location);


    List<Technician> findBySpecializationAndDeletedAtIsNull(Specialization specialization);



    Page<Technician> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndDeletedAtIsNull(
            String firstName, String lastName, Pageable pageable);


    Optional<Technician> findByEmailAndDeletedAtIsNull(String email);


    boolean existsByEmailAndDeletedAtIsNull(String email);


    Page<Technician> findByDeletedAtIsNull(Pageable pageable);


    Page<Technician> findByStatusAndLocationAndSpecializationAndDeletedAtIsNull(
            TechnicianStatus status, Location location, Specialization specialization, Pageable pageable);
    Optional<Technician> findByUserIdAndDeletedAtIsNull(Long id);

}
