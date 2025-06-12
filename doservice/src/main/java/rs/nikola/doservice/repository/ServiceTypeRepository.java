package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.ServiceType;

import java.util.List;
import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    // Svi aktivni (neobrisani) tipovi usluga
    List<ServiceType> findByDeletedAtIsNull();

    // Pronađi tip usluge po imenu (za validaciju ili selektor)
    Optional<ServiceType> findByNameAndDeletedAtIsNull(String name);

    // Proveri jedinstvenost imena (pre unosa ili izmene)
    boolean existsByNameAndDeletedAtIsNull(String name);

    // Pronađi sve tipove usluga (uključujući i obrisane, za admin/report)
    List<ServiceType> findAll();
}
