package rs.nikola.doservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.TaskFile;
import rs.nikola.doservice.entity.Task;


import java.util.List;
import java.util.Optional;

public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {

    // Pronađi sve fajlove za dati task (neobrisane)
    List<TaskFile> findByTaskAndDeletedAtIsNull(Task task);

    // Pronađi jedan fajl po ID-u, ali samo ako nije soft deleted
    Optional<TaskFile> findByIdAndDeletedAtIsNull(Long id);

    // Prikaži sve (neobrisane) fajlove
    List<TaskFile> findByDeletedAtIsNull();


}
