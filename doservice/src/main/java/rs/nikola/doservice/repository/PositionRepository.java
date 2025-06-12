package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    // Svi aktivni (neobrisani) nazivi pozicija
    List<Position> findByDeletedAtIsNull();

    // Pronađi poziciju po imenu (za validaciju ili selektor)
    Optional<Position> findByNameAndDeletedAtIsNull(String name);

    // Proveri jedinstvenost imena (za validaciju pri unosu ili izmeni)
    boolean existsByNameAndDeletedAtIsNull(String name);

    // Prikaži sve pozicije (uključuje i soft deleted, za admin/report)
    List<Position> findAll();
}
