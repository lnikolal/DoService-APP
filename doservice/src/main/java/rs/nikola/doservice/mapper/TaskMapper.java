package rs.nikola.doservice.mapper;

import rs.nikola.doservice.dto.TaskDto;
import rs.nikola.doservice.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDto fromEntity(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setClientName(task.getClientName());
        dto.setClientContact(task.getClientContact());
        dto.setClientAddress(task.getClientAddress());

        if (task.getServiceType() != null) {
            dto.setServiceTypeId(task.getServiceType().getId());
            dto.setServiceTypeName(task.getServiceType().getName());
        }

        if (task.getTaskStatus() != null) {
            dto.setTaskStatusId(task.getTaskStatus().getId());
            dto.setTaskStatusName(task.getTaskStatus().getName());
        }

        dto.setAssignedDate(task.getAssignedDate());
        dto.setCompletionDate(task.getCompletionDate());
        dto.setDescription(task.getDescription());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        if (task.getTechnicians() != null) {

            dto.setTechnicianIds(
                    task.getTechnicians().stream()
                            .map(Technician::getId)
                            .collect(Collectors.toSet())
            );
            dto.setTechnicianNames(
                    task.getTechnicians().stream()
                            .map(t -> t.getFirstName() + " " + t.getLastName())
                            .collect(Collectors.toSet())
            );
        }


        if (task.getTaskFiles() != null) {
            dto.setTaskFileIds(
                    task.getTaskFiles().stream()
                            .map(TaskFile::getId)
                            .collect(Collectors.toSet())
            );
            dto.setTaskFileUrls(
                    task.getTaskFiles().stream()
                            .map(TaskFile::getFileUrl)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    public static Task toEntity(
            TaskDto dto,
            ServiceType serviceType,
            TaskStatus taskStatus,
            Set<Technician> technicians,
            Set<TaskFile> taskFiles
    ) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setClientName(dto.getClientName());
        task.setClientContact(dto.getClientContact());
        task.setClientAddress(dto.getClientAddress());
        task.setServiceType(serviceType);
        task.setTaskStatus(taskStatus);
        task.setAssignedDate(dto.getAssignedDate());
        task.setCompletionDate(dto.getCompletionDate());
        task.setDescription(dto.getDescription());
        task.setCreatedAt(dto.getCreatedAt());
        task.setUpdatedAt(dto.getUpdatedAt());

        if (technicians != null) {
            task.setTechnicians(technicians);
        }
        if (taskFiles != null) {
            task.setTaskFiles(taskFiles);
        }

        return task;
    }

    public static Task fromDto(
            TaskDto dto,
            ServiceType serviceType,
            TaskStatus taskStatus,
            Set<Technician> technicians
    ) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setClientName(dto.getClientName());
        task.setClientContact(dto.getClientContact());
        task.setClientAddress(dto.getClientAddress());
        task.setDescription(dto.getDescription());
        task.setAssignedDate(dto.getAssignedDate());
        task.setCompletionDate(dto.getCompletionDate());

        if (serviceType != null) {
            task.setServiceType(serviceType);
        }
        if (taskStatus != null) {
            task.setTaskStatus(taskStatus);
        }
        if (technicians != null) {
            task.setTechnicians(technicians);
        }
        return task;
    }

    public static TaskDto toDto(Task task) {
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
        if (task.getTaskFiles() != null) {
            dto.setTaskFileIds(
                    task.getTaskFiles().stream()
                            .map(TaskFile::getId)
                            .collect(Collectors.toSet())
            );
            dto.setTaskFileUrls(
                    task.getTaskFiles().stream()
                            .map(TaskFile::getFileUrl)
                            .collect(Collectors.toSet())
            );
        }
        return dto;
    }
}
