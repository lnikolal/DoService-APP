package rs.nikola.doservice.controller.api;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


import rs.nikola.doservice.entity.Task;
import rs.nikola.doservice.mapper.TaskMapper;

import rs.nikola.doservice.service.ServiceTypeService;
import rs.nikola.doservice.service.TaskService;
import org.springframework.security.core.Authentication;
import rs.nikola.doservice.dto.TaskDto;
import rs.nikola.doservice.service.TaskStatusService;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class TaskApiController {

    private final TaskService taskService;

    private final TaskStatusService taskStatusService;
    private final ServiceTypeService serviceTypeService;

    public TaskApiController(TaskService taskService,
                             TaskStatusService taskStatusService,
                             ServiceTypeService serviceTypeService) {
        this.taskService = taskService;
        this.taskStatusService = taskStatusService;
        this.serviceTypeService = serviceTypeService;
    }


    @GetMapping
    public ResponseEntity<?> getTasksForCurrentUser(Authentication authentication) {
        String username = authentication.getName();


        List<TaskDto> tasks = taskService.getAllActive().stream()
                .map(TaskMapper::fromEntity)
                .toList();
        return ResponseEntity.ok(tasks);
    }



    @GetMapping("/edit/{id}")
    public ResponseEntity<TaskDto> showEditForm(@PathVariable Long id) {
        Task task = taskService.getById(id);
        TaskDto dto = TaskMapper.fromEntity(task);


        return ResponseEntity.ok(dto);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<TaskDto> updateTaskApi(@PathVariable Long id, @RequestBody @Valid TaskDto dto) {
        try {
            Task updated = taskService.update(id, dto);
            TaskDto responseDto = TaskMapper.fromEntity(updated);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/save")
    public ResponseEntity<Void> createTask( @RequestBody @Valid TaskDto dto) {
        try {

         taskService.create( dto);
       return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto dto) {
//        TaskDto saved = TaskMapper.fromEntity(taskService.create(dto));
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//    }
}
