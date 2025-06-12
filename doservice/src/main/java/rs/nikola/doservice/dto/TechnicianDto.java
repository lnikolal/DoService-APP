package rs.nikola.doservice.dto;

import org.springframework.web.multipart.MultipartFile;
import rs.nikola.doservice.entity.Technician;

import java.time.LocalDateTime;

public class TechnicianDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String status; // ili može TechnicianStatus ako želiš
    private Long specializationId;
    private String specializationName; // opcionalno
    private Long locationId;
    private String locationName; // opcionalno
    private LocalDateTime employmentDate;
    private String profileImageUrl;
    private MultipartFile profileImageFile;


    public MultipartFile getProfileImageFile() {
        return profileImageFile;
    }

    public void setProfileImageFile(MultipartFile profileImageFile) {
        this.profileImageFile = profileImageFile;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getSpecializationId() { return specializationId; }
    public void setSpecializationId(Long specializationId) { this.specializationId = specializationId; }

    public String getSpecializationName() { return specializationName; }
    public void setSpecializationName(String specializationName) { this.specializationName = specializationName; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public LocalDateTime getEmploymentDate() { return employmentDate; }
    public void setEmploymentDate(LocalDateTime employmentDate) { this.employmentDate = employmentDate; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }




}
