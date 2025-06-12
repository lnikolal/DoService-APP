package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Specialization;

import java.util.List;
import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    // Pronađi sve aktivne (neobrisane) specijalizacije
    List<Specialization> findByDeletedAtIsNull();

    // Pronađi specijalizaciju po imenu (npr. za validaciju ili selektor)
    Optional<Specialization> findByNameAndDeletedAtIsNull(String name);

    // Proveri da li postoji aktivna specijalizacija sa tim imenom (za validaciju unosa)
    boolean existsByNameAndDeletedAtIsNull(String name);

    // Pronađi sve specijalizacije (uključuje i obrisane, ako treba za admin/report)
    List<Specialization> findAll();
}
