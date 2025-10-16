package rs.nikola.doservice.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.nikola.doservice.dto.LookupDto;
import rs.nikola.doservice.dto.TaskDto;
import rs.nikola.doservice.dto.TechnicianDto;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.entity.TaskStatus;
import rs.nikola.doservice.entity.ServiceType;
import rs.nikola.doservice.mapper.TechnicianMapper;
import rs.nikola.doservice.service.ServiceTypeService;
import rs.nikola.doservice.service.TaskStatusService;
import rs.nikola.doservice.service.TechnicianService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lookups")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class LookupApiController {

    private final TechnicianService technicianService;
    private final TaskStatusService taskStatusService;
    private final ServiceTypeService serviceTypeService;

    public LookupApiController(TechnicianService technicianService,
                               TaskStatusService taskStatusService,
                               ServiceTypeService serviceTypeService) {
        this.technicianService = technicianService;
        this.taskStatusService = taskStatusService;
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping("/task-types")
    public ResponseEntity<Map<String, Object>> getLookups() {
        Map<String, Object> lookups = new HashMap<>();



        lookups.put("technicians", technicianService.getAllActive().stream()
        .map(tech -> new LookupDto(tech.getId(), tech.getFirstName()+" "+tech.getLastName()))
                .toList());

        lookups.put("serviceTypes", serviceTypeService.getAllActive().stream()
                .map(st -> new LookupDto(st.getId(), st.getName()))
                .toList());



        lookups.put("statuses", taskStatusService.getAllActive().stream()
                .map(s -> new LookupDto(s.getId(), s.getName()))
                .toList());

        return ResponseEntity.ok(lookups);
    }


    @GetMapping("/statuses")
    public List<LookupDto> getTaskStatuses() {
        return taskStatusService.getAllActive().stream()
                .map(s -> new LookupDto(s.getId(), s.getName()))
                .toList();
    }

    @GetMapping("/service-types")
    public List<LookupDto> getServiceTypes() {
        return serviceTypeService.getAllActive().stream()
                .map(t -> new LookupDto(t.getId(), t.getName()))
                .toList();
    }

}
