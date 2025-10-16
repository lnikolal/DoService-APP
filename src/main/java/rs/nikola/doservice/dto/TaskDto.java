package rs.nikola.doservice.dto;

import rs.nikola.doservice.entity.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskDto {
    private Long id;
    private String clientName;
    private String clientContact;
    private String clientAddress;
    private Long serviceTypeId;
    private String serviceTypeName;
    private Long taskStatusId;
    private String taskStatusName;
    private LocalDateTime assignedDate;
    private LocalDateTime completionDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Long> technicianIds;
    private Set<String> technicianNames;
    private Set<Long> taskFileIds;
    private Set<String> taskFileUrls;
    private List<MultipartFile> taskImageFiles;

    public Set<String> getTaskFileUrls() {
        return taskFileUrls;
    }

    public void setTaskFileUrls(Set<String> taskFileUrls) {
        this.taskFileUrls = taskFileUrls;
    }

    public List<MultipartFile> getTaskImageFiles() {
        return taskImageFiles;
    }

    public void setTaskImageFiles(List<MultipartFile> taskImageFiles) {
        this.taskImageFiles = taskImageFiles;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public Long getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Long> getTechnicianIds() {
        return technicianIds;
    }

    public void setTechnicianIds(Set<Long> technicianIds) {
        this.technicianIds = technicianIds;
    }

    public Set<String> getTechnicianNames() {
        return technicianNames;
    }

    public void setTechnicianNames(Set<String> technicianNames) {
        this.technicianNames = technicianNames;
    }

    public Set<Long> getTaskFileIds() {
        return taskFileIds;
    }

    public void setTaskFileIds(Set<Long> taskFileIds) {
        this.taskFileIds = taskFileIds;
    }


}
