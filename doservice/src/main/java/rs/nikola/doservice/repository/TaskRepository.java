package rs.nikola.doservice.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.Task;
import rs.nikola.doservice.entity.TaskStatus;
import rs.nikola.doservice.entity.Technician;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Svi taskovi koji nisu soft-deleted
    List<Task> findByDeletedAtIsNull();

    // Paginacija za aktivne
    Page<Task> findByDeletedAtIsNull(Pageable pageable);

    // Po statusu (PENDING, COMPLETED, IN_PROGRESS, ...)
    List<Task> findByTaskStatusAndDeletedAtIsNull(TaskStatus taskStatus);

    Page<Task> findByTaskStatusAndDeletedAtIsNull(TaskStatus taskStatus, Pageable pageable);

    // Pronađi taskove za određenog tehničara (many-to-many)
    List<Task> findByTechnicians_IdAndDeletedAtIsNull(Long id);

    // Pronađi po klijentovom imenu (delimično poklapanje), case-insensitive
    List<Task> findByClientNameContainingIgnoreCaseAndDeletedAtIsNull(String clientName);

    // Taskovi kreirani u zadatom periodu
    List<Task> findByAssignedDateBetweenAndDeletedAtIsNull(LocalDateTime start, LocalDateTime end);

    // Top N taskova po statusu, sortirano po datumu dodele (za dashboard)
    List<Task> findTop3ByTaskStatus_NameOrderByAssignedDateDesc(String statusName);

    List<Task> findAllByTaskStatus_NameAndDeletedAtIsNull(String statusName);
    // Pronađi task po ID-u, ali samo ako nije obrisan
    Optional<Task> findByIdAndDeletedAtIsNull(Long id);

    // Pronađi taskove po više statusa (lista statusa)
    List<Task> findByTaskStatus_NameInAndDeletedAtIsNull(List<String> statusNames);



    @Query("SELECT t.technicians FROM Task t WHERE t.id = :id AND t.deletedAt IS NULL")
    List<Technician> findTechniciansById(@Param("id") Long id);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.technicians WHERE t.id = :id AND t.deletedAt IS NULL")
    Optional<Task> findByIdWithTechnicians(@Param("id") Long id);



}
