package rs.nikola.doservice.repository;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import rs.nikola.doservice.entity.User;
import rs.nikola.doservice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Pronađi korisnika po korisničkom imenu (za login/autentikaciju)
    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    // Pronađi korisnika po email-u (za validaciju ili login)
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);
    // Proveri da li postoji aktivni korisnik sa zadatim username-om
    boolean existsByUsernameAndDeletedAtIsNull(String username);

    // Proveri da li postoji aktivni korisnik sa zadatim email-om
    boolean existsByEmailAndDeletedAtIsNull(String email);

    // Prikaži sve korisnike (samo neobrisane)
    List<User> findByDeletedAtIsNull();

    // Prikaži sve korisnike po roli
    List<User> findByRoleAndDeletedAtIsNull(Role role);

    // Paginacija za korisnike
    Page<User> findByDeletedAtIsNull(Pageable pageable);


}
