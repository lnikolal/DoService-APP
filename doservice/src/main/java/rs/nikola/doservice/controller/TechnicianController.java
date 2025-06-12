package rs.nikola.doservice.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.nikola.doservice.dto.TechnicianDto;
import rs.nikola.doservice.dto.TechnicianUserDto;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.mapper.TechnicianMapper;
import rs.nikola.doservice.service.*;

import java.util.List;

@Controller
@RequestMapping("/technicians")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class TechnicianController {

    private final TechnicianService technicianService;
    private final LocationService locationService;
    private final SpecializationService specializationService;
    private final PositionService positionService;

    public TechnicianController(
            TechnicianService technicianService,
            LocationService locationService,
            SpecializationService specializationService,
            PositionService positionService
    ) {
        this.technicianService = technicianService;
        this.locationService = locationService;
        this.specializationService = specializationService;
        this.positionService = positionService;
    }


    @GetMapping
    public String list(Model model) {

        List<TechnicianDto> dtot = technicianService.getAllActive().stream()
                .map(TechnicianMapper::fromEntity)
                .toList();
        model.addAttribute("technicians", dtot);
        return "technicians/list";
    }


    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("technicianUserDto", new TechnicianUserDto());
        addLookups(model);
        return "technicians/add_form";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("technicianUserDto") @Valid TechnicianUserDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addLookups(model);
            return "technicians/add_form";
        }
        technicianService.createWithUser(dto);
        return "redirect:/technicians";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        TechnicianDto tech = TechnicianMapper.fromEntity(technicianService.getById(id));


        model.addAttribute("technicianDto", tech);
        addLookups(model);
        return "technicians/edit_form";
    }
    @PostMapping("/edit")
    public String update(
            @ModelAttribute("technicianDto") @Valid TechnicianDto technician,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            addLookups(model);
            return "technicians/edit_form";
        }
        technicianService.editTechnician(technician);
        return "redirect:/technicians";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String deleteTechnician(@RequestParam Long id) {

        technicianService.softDelete(id);
        return "redirect:/technicians";
    }
    @PostMapping("/status")
    public String changeStatus(@RequestParam Long id, @RequestParam String status) {
        technicianService.setStatus(id, status);
        return "redirect:/technicians";
    }


    private void addLookups(Model model) {
        model.addAttribute("locations", locationService.getAllActive());
        model.addAttribute("specializations", specializationService.getAllActive());

    }
}
