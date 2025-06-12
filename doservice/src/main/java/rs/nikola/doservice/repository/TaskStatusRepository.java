package rs.nikola.doservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    // Svi aktivni (neobrisani) statusi taskova
    List<TaskStatus> findByDeletedAtIsNull();

    // Pronađi status po imenu (za validaciju, selektor, ili brzi lookup)
    Optional<TaskStatus> findByNameAndDeletedAtIsNull(String name);

    // Proveri jedinstvenost imena (pre unosa/izmene)
    boolean existsByNameAndDeletedAtIsNull(String name);

    // Prikaži sve statuse (uključujući i soft deleted, npr. za admin/report)
    List<TaskStatus> findAll();
}
