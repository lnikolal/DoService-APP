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

    // Svi "živi" (neobrisani) tehničari
    List<Technician> findByDeletedAtIsNull();

    // Pronađi po statusu (samo aktivni, neobrisani)
    List<Technician> findByStatusAndDeletedAtIsNull(TechnicianStatus status);

    Optional<Technician>  findByIdAndDeletedAtIsNull(Long id);

    // Pronađi po lokaciji
    List<Technician> findByLocationAndDeletedAtIsNull(Location location);

    // Pronađi po specijalizaciji
    List<Technician> findBySpecializationAndDeletedAtIsNull(Specialization specialization);

    // Pretraga po imenu ili prezimenu (case-insensitive), paginacija
    Page<Technician> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndDeletedAtIsNull(
            String firstName, String lastName, Pageable pageable);

    // Pretraga po emailu (za login ili validaciju)
    Optional<Technician> findByEmailAndDeletedAtIsNull(String email);

    // Provera jedinstvenosti email-a (za validaciju pri unosu)
    boolean existsByEmailAndDeletedAtIsNull(String email);

    // Paginacija svih aktivnih
    Page<Technician> findByDeletedAtIsNull(Pageable pageable);

    // Po statusu, lokaciji i specijalizaciji (primer za napredniji filter)
    Page<Technician> findByStatusAndLocationAndSpecializationAndDeletedAtIsNull(
            TechnicianStatus status, Location location, Specialization specialization, Pageable pageable);
    Optional<Technician> findByUserIdAndDeletedAtIsNull(Long id);

}
