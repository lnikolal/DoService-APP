package rs.nikola.doservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rs.nikola.doservice.service.TaskService;
import rs.nikola.doservice.service.UserService;


@Controller
public class DashboardController {

    private final TaskService taskService;
    private final UserService userService;


    public DashboardController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }


    @GetMapping("/login")
    public String dashboard(Model model) {
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        // Korisničko ime i rola
        String username = userDetails.getUsername();

        String role = userDetails.getAuthorities().stream()
                .findFirst().map(auth -> auth.getAuthority()).orElse("");

        model.addAttribute("username", username);
        model.addAttribute("role", role);

        // Admin i Manager — prikaz svih taskova po statusima
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_MANAGER")) {
            model.addAttribute("tasksCompleted", taskService.getByStatusName("COMPLETED"));
            model.addAttribute("tasksPending", taskService.getByStatusName("PENDING"));
            model.addAttribute("tasksInProgress", taskService.getByStatusName("IN PROGRESS"));
            model.addAttribute("tasksCancelled", taskService.getByStatusName("CANCELLED"));
            // Dodaj i druge statistike, npr. broj korisnika, broj tehničara...
        }
        // Tehničar — vidi samo svoje taskove
        else if (role.equals("ROLE_TECHNICIAN")) {
           model.addAttribute("tasksCompleted", taskService.getTasksForTechnician("COMPLETED", username));
           model.addAttribute("tasksPENDING", taskService.getTasksForTechnician("PENDING", username));
           model.addAttribute("tasksInProgress", taskService.getTasksForTechnician("IN PROGRESS", username));
           model.addAttribute("tasksCancelled", taskService.getTasksForTechnician("CANCELLED", username));
        }
        // Ostalo — fallback
        else {
            model.addAttribute("tasksCompleted", null);
            model.addAttribute("tasksWaiting", null);
            model.addAttribute("tasksInProgress", null);
        }

        return "dashboard"; // dashboard.html Thymeleaf template
    }
}
