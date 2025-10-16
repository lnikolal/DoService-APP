package rs.nikola.doservice.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Contract;
import rs.nikola.doservice.entity.Position;
import rs.nikola.doservice.entity.Technician;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByDeletedAtIsNull();

    Page<Contract> findByDeletedAtIsNull(Pageable pageable);

    List<Contract> findByTechnicianAndDeletedAtIsNull(Technician technician);

    List<Contract> findByPositionAndDeletedAtIsNull(Position position);

    List<Contract> findByCreatedAtBetweenAndDeletedAtIsNull(LocalDateTime start, LocalDateTime end);

    boolean existsByTechnicianAndEndDateIsNullAndDeletedAtIsNull(Technician technician);

    Optional<Contract> findByIdAndDeletedAtIsNull(Long id);

    Page<Contract> findByTechnicianAndDeletedAtIsNull(Technician technician, Pageable pageable);


}
