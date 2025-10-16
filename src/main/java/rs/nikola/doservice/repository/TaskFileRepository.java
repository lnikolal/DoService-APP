package rs.nikola.doservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.TaskFile;
import rs.nikola.doservice.entity.Task;


import java.util.List;
import java.util.Optional;

public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {


    List<TaskFile> findByTaskAndDeletedAtIsNull(Task task);


    Optional<TaskFile> findByIdAndDeletedAtIsNull(Long id);


    List<TaskFile> findByDeletedAtIsNull();


}
