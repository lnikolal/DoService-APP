package rs.nikola.doservice.mapper;

import rs.nikola.doservice.dto.TechnicianDto;
import rs.nikola.doservice.entity.Technician;

public class TechnicianMapper {

    public static TechnicianDto fromEntity(Technician technician) {
        TechnicianDto dto = new TechnicianDto();
        dto.setId(technician.getId());
        dto.setFirstName(technician.getFirstName());
        dto.setLastName(technician.getLastName());
        dto.setEmail(technician.getEmail());
        dto.setPhone(technician.getPhone());
        dto.setStatus(technician.getStatus().name());


        if (technician.getSpecialization() != null) {
            dto.setSpecializationId(technician.getSpecialization().getId());
            dto.setSpecializationName(technician.getSpecialization().getName());
        }
        if (technician.getLocation() != null) {
            dto.setLocationId(technician.getLocation().getId());
            dto.setLocationName(technician.getLocation().getName());
        }

        dto.setEmploymentDate(technician.getEmploymentDate());
        dto.setProfileImageUrl(technician.getProfileImageUrl());
        return dto;
    }
}
