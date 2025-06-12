package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.ServiceType;

import java.util.List;
import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {


    List<ServiceType> findByDeletedAtIsNull();


    Optional<ServiceType> findByNameAndDeletedAtIsNull(String name);


    boolean existsByNameAndDeletedAtIsNull(String name);


    List<ServiceType> findAll();
}
