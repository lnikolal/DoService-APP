package rs.nikola.doservice.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.nikola.doservice.dto.TaskDto;
import rs.nikola.doservice.entity.Task;
import rs.nikola.doservice.mapper.TaskMapper;
import rs.nikola.doservice.service.ServiceTypeService;
import rs.nikola.doservice.service.TaskService;
import rs.nikola.doservice.service.TaskStatusService;
import rs.nikola.doservice.service.TechnicianService;


@Controller
@RequestMapping("/tasks")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class TaskController {

    private final TaskService taskService;
    private final TechnicianService technicianService;
    private final TaskStatusService taskStatusService;
    private final ServiceTypeService serviceTypeService;

    public TaskController(TaskService taskService,
                          TechnicianService technicianService,
                          TaskStatusService taskStatusService,
                          ServiceTypeService serviceTypeService) {
        this.taskService = taskService;
        this.technicianService = technicianService;
        this.taskStatusService = taskStatusService;
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllActive());
        return "tasks/list";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        addLookups(model);
        return "tasks/form";
    }

    @PostMapping("/save")
    public String createTask(@ModelAttribute("taskDto") @Valid TaskDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addLookups(model);
            return "tasks/form";
        }
        taskService.create(dto);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Task task = taskService.getById(id);
        TaskDto dto = TaskMapper.toDto(task);
        model.addAttribute("taskDto", dto);
        addLookups(model);
        return "tasks/form";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("taskDto") @Valid TaskDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addLookups(model);
            return "tasks/form";
        }
        taskService.update(id, dto);
        return "redirect:/tasks";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String deleteTechnician(@RequestParam Long id) {

        taskService.softDelete(id);
        return "redirect:/tasks";
    }

    @PostMapping("/status")
    public String changeStatus(@RequestParam Long id, @RequestParam String status) {
        taskService.setStatusCancel(id, status);
        return "redirect:/tasks";
    }

    @GetMapping("/status")
    public String showTaskByStatus(@RequestParam String status, Model model) {
        model.addAttribute("tasks",taskService.getByStatusName(status));
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String taskDetails(@PathVariable Long id, Model model) {
        Task task = taskService.getById(id);
        model.addAttribute("task", task);
        return "task/details";
    }
    private void addLookups(Model model) {
        model.addAttribute("technicians", technicianService.getAllActive());
        model.addAttribute("statuses", taskStatusService.getAllActive());
        model.addAttribute("serviceTypes", serviceTypeService.getAllActive());
    }
}
