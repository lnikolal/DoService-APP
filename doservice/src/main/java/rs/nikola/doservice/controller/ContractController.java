package rs.nikola.doservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.nikola.doservice.dto.ContractDto;
import rs.nikola.doservice.entity.Contract;
import rs.nikola.doservice.entity.ContractFile;
import rs.nikola.doservice.entity.Position;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.mapper.ContractMapper;
import rs.nikola.doservice.service.ContractFileService;
import rs.nikola.doservice.service.ContractService;
import rs.nikola.doservice.service.PositionService;
import rs.nikola.doservice.service.TechnicianService;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/contracts")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public class ContractController {

    private final ContractService contractService;
    private final TechnicianService technicianService;
    private final PositionService positionService;
    private final ContractFileService contractFileService;

    public ContractController(ContractService contractService, TechnicianService technicianService, PositionService positionService, ContractFileService contractFileService) {
        this.contractService = contractService;
        this.technicianService = technicianService;
        this.positionService = positionService;
        this.contractFileService =contractFileService;
    }


    @GetMapping
    public String listContracts(Model model) {
        List<Contract> contracts = contractService.getAllActive();
        model.addAttribute("contracts", contracts);
        return "contracts/list"; // kreiraj contracts/list.html
    }

    // Forma za dodavanje novog ugovora
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("contractDto", new ContractDto());
        addLookups(model);
        return "contracts/form"; // kreiraj contracts/form.html
    }



    @PostMapping("/save")
    public String saveContract(
            @ModelAttribute ContractDto contractDto,
            @RequestParam("contractFiles") MultipartFile[] pdfFiles,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            addLookups(model);
            return "contracts/form";
        }

        Technician tech = technicianService.getById(contractDto.getTechnicianId());
        Position pos = positionService.getById(contractDto.getPositionId());
        Contract contract = ContractMapper.toEntity(contractDto, tech, pos);

        contract = contractService.save(contract);

        contractFileService.saveFilesForContract(contract, pdfFiles);

        return "redirect:/contracts";
    }


    @GetMapping("/edit/{id}")
    public String editContractForm(@PathVariable Long id, Model model) {
        Contract contract = contractService.getById(id);
        ContractDto dto = ContractMapper.toDto(contract);

        List<ContractFile> contractFiles = contractFileService.getFilesForContract(contract);

        model.addAttribute("contractDto", dto);
        model.addAttribute("contractFiles", contractFiles);
        addLookups(model);

        return "contracts/form";
    }
    @PostMapping("/edit/{id}")
    public String updateContract(
            @PathVariable Long id,
            @ModelAttribute ContractDto contractDto,
            @RequestParam(value = "contractFiles", required = false) MultipartFile[] pdfFiles,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            addLookups(model);
            return "contracts/form";
        }
        Technician tech = technicianService.getById(contractDto.getTechnicianId());
        Position pos = positionService.getById(contractDto.getPositionId());
        Contract updated = ContractMapper.toEntity(contractDto, tech, pos);

        Contract contract = contractService.update(id, updated);
        contractFileService.saveFilesForContract(contract, pdfFiles);

        return "redirect:/contracts";
    }




    @PostMapping("/delete")
    public String deleteContract(@RequestParam Long id) {
        contractService.softDelete(id);
        return "redirect:/contracts";
    }

    private void addLookups(Model model) {
        model.addAttribute("technicians", technicianService.getAllActive());
        model.addAttribute("positions", positionService.getAllActive());
    }
}