package rs.nikola.doservice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.nikola.doservice.entity.Contract;
import rs.nikola.doservice.entity.ContractFile;
import rs.nikola.doservice.entity.Position;
import rs.nikola.doservice.entity.Technician;
import rs.nikola.doservice.repository.ContractFileRepository;
import rs.nikola.doservice.repository.ContractRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractFileRepository contractFileRepository;
    private final FileStorageService fileStorageService;
    public ContractService(ContractRepository contractRepository,ContractFileRepository contractFileRepository,FileStorageService fileStorageService) {
        this.contractRepository = contractRepository;
        this.contractFileRepository = contractFileRepository;
        this.fileStorageService = fileStorageService;
    }


    public List<Contract> getAllActive() {
        return contractRepository.findByDeletedAtIsNull();
    }


    public Page<Contract> getAllActive(Pageable pageable) {
        return contractRepository.findByDeletedAtIsNull(pageable);
    }


    public Contract getById(Long id) {
        return contractRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Contract not found"));
    }


    public List<Contract> getByTechnician(Technician technician) {
        return contractRepository.findByTechnicianAndDeletedAtIsNull(technician);
    }


    public List<Contract> getByPosition(Position position) {
        return contractRepository.findByPositionAndDeletedAtIsNull(position);
    }


    public List<Contract> getBySignedDateBetween(LocalDateTime start, LocalDateTime end) {
        return contractRepository.findByCreatedAtBetweenAndDeletedAtIsNull(start, end);
    }


    public Page<Contract> getByTechnician(Technician technician, Pageable pageable) {
        return contractRepository.findByTechnicianAndDeletedAtIsNull(technician, pageable);
    }


    public boolean hasActiveContract(Technician technician) {
        return contractRepository.existsByTechnicianAndEndDateIsNullAndDeletedAtIsNull(technician);
    }

    public Contract save(Contract contract) {
        contract.setCreatedAt(LocalDateTime.now());
        contract.setUpdatedAt(LocalDateTime.now());
        contract.setDeletedAt(null);
        return contractRepository.save(contract);
    }

    public Contract update(Long id, Contract newData) {
        Contract existing = getById(id);
        existing.setPosition(newData.getPosition());
        existing.setTechnician(newData.getTechnician());
        existing.setSalary(newData.getSalary());
        existing.setStartDate(newData.getStartDate());
        existing.setEndDate(newData.getEndDate());
        existing.setUpdatedAt(LocalDateTime.now());

        return contractRepository.save(existing);
    }

    public void softDelete(Long id) {
        Contract contract = getById(id);
        contract.setDeletedAt(LocalDateTime.now());
        contractRepository.save(contract);
    }


    public Optional<Contract> findOptionalById(Long id) {
        return contractRepository.findByIdAndDeletedAtIsNull(id);
    }
}
