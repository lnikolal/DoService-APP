package rs.nikola.doservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {


    List<TaskStatus> findByDeletedAtIsNull();


    Optional<TaskStatus> findByNameAndDeletedAtIsNull(String name);


    boolean existsByNameAndDeletedAtIsNull(String name);


    List<TaskStatus> findAll();
}
