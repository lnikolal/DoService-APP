package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(String name);


    List<Role> findByDeletedAtIsNull();

    boolean existsByNameAndDeletedAtIsNull(String name);


    List<Role> findAll();
}

