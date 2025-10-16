package rs.nikola.doservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.nikola.doservice.dto.UserDto;
import rs.nikola.doservice.entity.Role;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.entity.User;
import rs.nikola.doservice.mapper.UserMapper;
import rs.nikola.doservice.service.RoleService;
import rs.nikola.doservice.service.TechnicianService;
import rs.nikola.doservice.service.UserService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final TechnicianService technicianService;

    public UserController(UserService userService, RoleService roleService, TechnicianService technicianService) {
        this.userService = userService;
        this.roleService = roleService;
        this.technicianService = technicianService;
    }


    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAllActive()
                .stream().map(UserMapper::toDto).collect(Collectors.toList());
        model.addAttribute("users", users);
        return "admin/users/list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleService.getAll());
        return "admin/users/form";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("userDto", UserMapper.toDto(user));
        model.addAttribute("roles", roleService.getAll());
        return "admin/users/form";
    }

    // SAVE/UPDATE USER
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("userDto") @Valid UserDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.getAll());
            return "admin/users/form";
        }


            userService.saveFromDto(dto);

        return "redirect:/users";
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        Technician technician = technicianService.getTechnicianByUserId(id);

        technicianService.softDelete(technician.getId());


        return "redirect:/users";
    }


}
