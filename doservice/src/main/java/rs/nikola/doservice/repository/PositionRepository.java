package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {


    List<Position> findByDeletedAtIsNull();


    Optional<Position> findByNameAndDeletedAtIsNull(String name);


    boolean existsByNameAndDeletedAtIsNull(String name);


    List<Position> findAll();
}
