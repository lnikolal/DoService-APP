package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Pronađi rolu po imenu (npr. "ROLE_ADMIN")
    Optional<Role> findByName(String name);

    // Prikaži sve neobrisane role (ako koristiš soft delete)
    List<Role> findByDeletedAtIsNull();

    // Proveri jedinstvenost po imenu (za validaciju pri unosu/izmeni)
    boolean existsByNameAndDeletedAtIsNull(String name);

    // Prikaži sve role (uključuje i obrisane ako trebaš kompletnu listu)
    List<Role> findAll();
}
