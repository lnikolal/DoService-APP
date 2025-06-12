
package rs.nikola.doservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.nikola.doservice.dto.TaskDto;
import rs.nikola.doservice.entity.Task;
import rs.nikola.doservice.entity.TaskStatus;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.repository.TaskRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TaskService_ {

    private final TaskRepository taskRepository;
    private final TechnicianService technicianService;
    private final TaskStatusService taskStatusService;
    private final ServiceTypeService serviceTypeService;

    public TaskService_(TaskRepository taskRepository,
                       TechnicianService technicianService,
                       TaskStatusService taskStatusService,
                       ServiceTypeService serviceTypeService) {
        this.taskRepository = taskRepository;
        this.technicianService = technicianService;
        this.taskStatusService = taskStatusService;
        this.serviceTypeService = serviceTypeService;
    }

    // DTO -> Entity
    public Task fromDto(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setClientName(dto.getClientName());
        task.setClientContact(dto.getClientContact());
        task.setClientAddress(dto.getClientAddress());
        task.setDescription(dto.getDescription());
        task.setAssignedDate(dto.getAssignedDate());
        task.setCompletionDate(dto.getCompletionDate());

        if (dto.getServiceTypeId() != null) {
            task.setServiceType(serviceTypeService.getById(dto.getServiceTypeId()));
        }
        if (dto.getTaskStatusId() != null) {
            task.setTaskStatus(taskStatusService.getById(dto.getTaskStatusId()));
        }
        if (dto.getTechnicianIds() != null) {
            Set<Technician> techs = dto.getTechnicianIds().stream()
                    .map(technicianService::getById)
                    .collect(Collectors.toSet());
            task.setTechnicians(techs);
        }
        return task;
    }

    // Entity -> DTO
    public TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setClientName(task.getClientName());
        dto.setClientContact(task.getClientContact());
        dto.setClientAddress(task.getClientAddress());
        dto.setDescription(task.getDescription());
        dto.setAssignedDate(task.getAssignedDate());
        dto.setCompletionDate(task.getCompletionDate());
        if (task.getServiceType() != null) {
            dto.setServiceTypeId(task.getServiceType().getId());
            dto.setServiceTypeName(task.getServiceType().getName());
        }
        if (task.getTaskStatus() != null) {
            dto.setTaskStatusId(task.getTaskStatus().getId());
            dto.setTaskStatusName(task.getTaskStatus().getName());
        }
        if (task.getTechnicians() != null) {
            dto.setTechnicianIds(
                    task.getTechnicians().stream()
                            .map(Technician::getId)
                            .collect(Collectors.toSet())
            );
        }
        return dto;
    }

    // --- CRUD za Task preko DTO-a ---

    public List<Task> findAll() {
        return taskRepository.findByDeletedAtIsNull();
    }

    public Task findById(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    public void create(TaskDto dto) {
        Task task = fromDto(dto);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void update(Long id, TaskDto dto) {
        Task task = findById(id);
        // Mapiraj DTO na postojeći entitet
        task.setClientName(dto.getClientName());
        task.setClientContact(dto.getClientContact());
        task.setClientAddress(dto.getClientAddress());
        task.setDescription(dto.getDescription());
        task.setAssignedDate(dto.getAssignedDate());
        task.setCompletionDate(dto.getCompletionDate());
        if (dto.getServiceTypeId() != null) {
            task.setServiceType(serviceTypeService.getById(dto.getServiceTypeId()));
        }
        if (dto.getTaskStatusId() != null) {
            task.setTaskStatus(taskStatusService.getById(dto.getTaskStatusId()));
        }
        if (dto.getTechnicianIds() != null) {
            Set<Technician> techs = dto.getTechnicianIds().stream()
                    .map(technicianService::getById)
                    .collect(Collectors.toSet());
            task.setTechnicians(techs);
        }
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }
}
