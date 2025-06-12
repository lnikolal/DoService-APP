package rs.nikola.doservice.service;


import org.springframework.stereotype.Service;
import rs.nikola.doservice.entity.ServiceType;
import rs.nikola.doservice.entity.TaskStatus;
import rs.nikola.doservice.repository.TaskStatusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class TaskStatusService {
    private TaskStatusRepository taskStatusRepository;
    public TaskStatusService(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }


    public List<TaskStatus> getAllActive() {
        return taskStatusRepository.findByDeletedAtIsNull();
    }

    public TaskStatus getById(Long id) {
        return taskStatusRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    public TaskStatus save(TaskStatus taskStatus) {
        if(taskStatusRepository.existsByNameAndDeletedAtIsNull(taskStatus.getName()))
            throw new IllegalArgumentException("Task already exists");
        return taskStatusRepository.save(taskStatus);
    }

    public void softDelete(Long id){
        TaskStatus taskStatus= getById(id);
        taskStatus.setDeletedAt(LocalDateTime.now());
        taskStatusRepository.save(taskStatus);
    }

   public Optional<TaskStatus> findByName(String name) {
        return taskStatusRepository.findByNameAndDeletedAtIsNull(name);
   }

}
