package rs.nikola.doservice.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class TechnicianUserDto {


    private Long technicianId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Technician email must be valid")
    @NotBlank(message = "Technician email is required")
    private String technicianEmail;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Status is required")
    private String status; // Enum: "ACTIVE", "INACTIVE"

    @NotNull(message = "Specialization ID is required")
    private Long specializationId;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @NotNull(message = "Employment date is required")
    private LocalDateTime employmentDate;

    private MultipartFile profileImageFile;
    private String profileImageUrl;


    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "User email must be valid")
    @NotBlank(message = "User email is required")
    private String userEmail;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role ID is required")
    private Long roleId;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MultipartFile getProfileImageFile() { return profileImageFile; }
    public void setProfileImageFile(MultipartFile profileImageFile) { this.profileImageFile = profileImageFile; }


    public Long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }

    public @NotBlank(message = "First name is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is required") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Technician email must be valid") @NotBlank(message = "Technician email is required") String getTechnicianEmail() {
        return technicianEmail;
    }

    public void setTechnicianEmail(@Email(message = "Technician email must be valid") @NotBlank(message = "Technician email is required") String technicianEmail) {
        this.technicianEmail = technicianEmail;
    }

    public @NotBlank(message = "Phone is required") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone is required") String phone) {
        this.phone = phone;
    }

    public @NotNull(message = "Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") String status) {
        this.status = status;
    }

    public @NotNull(message = "Specialization ID is required") Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(@NotNull(message = "Specialization ID is required") Long specializationId) {
        this.specializationId = specializationId;
    }

    public @NotNull(message = "Location ID is required") Long getLocationId() {
        return locationId;
    }

    public void setLocationId(@NotNull(message = "Location ID is required") Long locationId) {
        this.locationId = locationId;
    }

    public @NotNull(message = "Employment date is required") LocalDateTime getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(@NotNull(message = "Employment date is required") LocalDateTime employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    public @Email(message = "User email must be valid") @NotBlank(message = "User email is required") String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@Email(message = "User email must be valid") @NotBlank(message = "User email is required") String userEmail) {
        this.userEmail = userEmail;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotNull(message = "Role ID is required") Long getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull(message = "Role ID is required") Long roleId) {
        this.roleId = roleId;
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
}
