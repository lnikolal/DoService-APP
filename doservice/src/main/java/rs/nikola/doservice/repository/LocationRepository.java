package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // Svi aktivni (neobrisani) gradovi/lokacije
    List<Location> findByDeletedAtIsNull();

    // Pronađi lokaciju po imenu (za validaciju ili selektor)
    Optional<Location> findByNameAndDeletedAtIsNull(String name);

    // Proveri da li postoji aktivna lokacija sa zadatim imenom
    boolean existsByNameAndDeletedAtIsNull(String name);

    // Pronađi sve lokacije (uključujući i soft deleted, za admin/report)
    List<Location> findAll();

    Optional<Location> findById(Long id);
}
