package rs.nikola.doservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.nikola.doservice.dto.TaskDto;
import rs.nikola.doservice.entity.*;
import rs.nikola.doservice.mapper.TaskMapper;
import rs.nikola.doservice.repository.TaskFileRepository;
import rs.nikola.doservice.repository.TaskRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TechnicianService technicianService;
    private final TaskStatusService taskStatusService;
    private final ServiceTypeService serviceTypeService;


    private final TaskFileRepository taskFileRepository;
    private final FileStorageService fileStorageService;

    public TaskService(
            TaskRepository taskRepository,
            TechnicianService technicianService,
            TaskStatusService taskStatusService,
            ServiceTypeService serviceTypeService,
            TaskFileRepository taskFileRepository,
            FileStorageService fileStorageService) {
        this.taskRepository = taskRepository;
        this.technicianService = technicianService;
        this.taskStatusService = taskStatusService;
        this.serviceTypeService = serviceTypeService;
        this.taskFileRepository = taskFileRepository;
        this.fileStorageService = fileStorageService;
    }
    // =================== DTO <-> Entity ===================



    // =================== CRUD & Pretraga ===================

    public List<Task> getAllActive () {
        return taskRepository.findByDeletedAtIsNull();
    }

    public Page<Task> getAllActive(Pageable pageable) {
        return taskRepository.findByDeletedAtIsNull(pageable);
    }

    public Task getById(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    public Optional<Task> getOptionalById(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id);
    }

    public Task getByIdWithTechnicians(Long id) {
        return taskRepository.findByIdWithTechnicians(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    public List<Task> getByStatus(TaskStatus status) {
        return taskRepository.findByTaskStatusAndDeletedAtIsNull(status);
    }

    public List<Task> getByStatusName(String name) {
        return taskRepository.findAllByTaskStatus_NameAndDeletedAtIsNull(name);
    }

    public List<Task> getByStatusNames(List<String> statusNames) {
        return taskRepository.findByTaskStatus_NameInAndDeletedAtIsNull(statusNames);
    }

    public Page<Task> getByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findByTaskStatusAndDeletedAtIsNull(status, pageable);
    }

    public List<Task> getByClientName(String namePart) {
        return taskRepository.findByClientNameContainingIgnoreCaseAndDeletedAtIsNull(namePart);
    }

    public List<Task> getByAssignedDateBetween(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByAssignedDateBetweenAndDeletedAtIsNull(start, end);
    }

    public List<Task> getTop3ByStatus(String statusName) {
        return taskRepository.findTop3ByTaskStatus_NameOrderByAssignedDateDesc(statusName);
    }

    public boolean exists(Long id) {
        return taskRepository.findByIdAndDeletedAtIsNull(id).isPresent();
    }

    // =================== Kreiranje, ažuriranje, brisanje ===================

    public Task create(TaskDto dto) {
        ServiceType serviceType = null;
        if (dto.getServiceTypeId() != null) {
            serviceType = serviceTypeService.getById(dto.getServiceTypeId());
        }

        TaskStatus taskStatus = null;
        if (dto.getTaskStatusId() != null) {
            taskStatus = taskStatusService.getById(dto.getTaskStatusId());
        }

        Set<Technician> techs = null;
        if (dto.getTechnicianIds() != null) {
            techs = dto.getTechnicianIds().stream()
                    .map(technicianService::getById)
                    .collect(Collectors.toSet());
        }

        Task task = TaskMapper.fromDto(dto, serviceType, taskStatus, techs);


        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setDeletedAt(null);

        if (dto.getTaskImageFiles() != null) {
            for (MultipartFile file : dto.getTaskImageFiles()) {
                if (file != null && !file.isEmpty()) {
                    String url = fileStorageService.store(file);
                    TaskFile taskFile = new TaskFile();
                    taskFile.setTask(task);
                    taskFile.setFileUrl(url);
                    task.getTaskFiles().add(taskFile);

                }
            }
        }



        return taskRepository.save(task);
    }


    public Task update(Long id, TaskDto dto) {


        Task task = getById(id);
        task.setClientName(dto.getClientName());
        task.setClientContact(dto.getClientContact());
        task.setClientAddress(dto.getClientAddress());
        task.setDescription(dto.getDescription());
        task.setCompletionDate(dto.getCompletionDate());
        if (dto.getServiceTypeId() != null) {
            task.setServiceType(serviceTypeService.getById(dto.getServiceTypeId()));
        }
        if (dto.getTaskStatusId() != null) {
            task.setTaskStatus(taskStatusService.getById(dto.getTaskStatusId()));
        }

        if (dto.getAssignedDate() != null) {
            task.setAssignedDate(dto.getAssignedDate());
        }
        if (dto.getTechnicianIds() != null) {
            Set<Technician> techs = dto.getTechnicianIds().stream()
                    .map(technicianService::getById)
                    .collect(Collectors.toSet());
            task.setTechnicians(techs);
        }
        task.setUpdatedAt(LocalDateTime.now());


        if (dto.getTaskImageFiles() != null) {
            for (MultipartFile file : dto.getTaskImageFiles()) {
                if (file != null && !file.isEmpty()) {
                    String url = fileStorageService.store(file);
                    TaskFile taskFile = new TaskFile();
                    taskFile.setTask(task);
                    taskFile.setFileUrl(file.getOriginalFilename());
                    taskFile.setFileUrl(url);

                    task.getTaskFiles().add(taskFile);
                }
            }
        }


        return taskRepository.save(task);
    }


    public void softDelete(Long id) {
        Task task = getById(id);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }



    public List<Technician> getTechniciansForTask(Long taskId) {
        return taskRepository.findTechniciansById(taskId);
    }

    public List<Task> getTasksForTechnician(Long technicianId) {
        return taskRepository.findByTechnicians_IdAndDeletedAtIsNull(technicianId);
    }

    public List<Task> getTasksForTechnician(String status, String technicianUsername) {
        Technician t = technicianService.getTechnicianbyUsername(technicianUsername);
        List<Task> tasks = getTasksForTechnician(t.getId());
        return tasks.stream()
                .filter(task -> task.getTaskStatus().getName().equalsIgnoreCase(status))
                .toList();
    }

    public Task addTechnicianToTask(Long taskId, Technician technician) {
        Task task = getByIdWithTechnicians(taskId);
        task.getTechnicians().add(technician);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task removeTechnicianFromTask(Long taskId, Technician technician) {
        Task task = getByIdWithTechnicians(taskId);
        task.getTechnicians().remove(technician);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }


    public void setStatusCancel(Long id, String status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found!"));
        task.setTaskStatus(taskStatusService.findByName(status).orElseThrow());
        taskRepository.save(task);
    }
}
