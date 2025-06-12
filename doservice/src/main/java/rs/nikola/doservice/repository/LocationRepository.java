package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {


    List<Location> findByDeletedAtIsNull();


    Optional<Location> findByNameAndDeletedAtIsNull(String name);


    boolean existsByNameAndDeletedAtIsNull(String name);


    List<Location> findAll();

    Optional<Location> findById(Long id);
}
