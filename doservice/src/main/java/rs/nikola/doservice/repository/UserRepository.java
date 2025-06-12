package rs.nikola.doservice.repository;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import rs.nikola.doservice.entity.User;
import rs.nikola.doservice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsernameAndDeletedAtIsNull(String username);


    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByUsernameAndDeletedAtIsNull(String username);


    boolean existsByEmailAndDeletedAtIsNull(String email);


    List<User> findByDeletedAtIsNull();


    List<User> findByRoleAndDeletedAtIsNull(Role role);


    Page<User> findByDeletedAtIsNull(Pageable pageable);


}
