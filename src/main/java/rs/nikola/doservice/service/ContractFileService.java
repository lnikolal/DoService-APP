package rs.nikola.doservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.nikola.doservice.entity.Contract;
import rs.nikola.doservice.entity.ContractFile;
import rs.nikola.doservice.repository.ContractFileRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractFileService {

    private final ContractFileRepository contractFileRepository;
    private final FileStorageService fileStorageService;

    public ContractFileService(
            ContractFileRepository contractFileRepository,
            FileStorageService fileStorageService
    ) {
        this.contractFileRepository = contractFileRepository;
        this.fileStorageService = fileStorageService;
    }


    public void saveFilesForContract(Contract contract, MultipartFile[] pdfFiles) {
        if (pdfFiles == null) return;
        for (MultipartFile file : pdfFiles) {
            if (file != null && !file.isEmpty()) {
                String fileUrl = fileStorageService.store(file);
                ContractFile contractFile = new ContractFile();
                contractFile.setContract(contract);


                contractFile.setFileUrl(fileUrl);

                contractFile.setCreatedAt(LocalDateTime.now());
                contractFile.setDeletedAt(null);
                contractFileRepository.save(contractFile);
            }
        }
    }


    public List<ContractFile> getFilesForContract(Contract contract) {
        return contractFileRepository.findByContractAndDeletedAtIsNull(contract);
    }


    public void softDelete(Long id) {
        Optional<ContractFile> fileOpt = contractFileRepository.findByIdAndDeletedAtIsNull(id);
        if (fileOpt.isPresent()) {
            ContractFile file = fileOpt.get();
            file.setDeletedAt(LocalDateTime.now());
            contractFileRepository.save(file);
        }
    }


    public List<String> getFileUrlsForContract(Contract contract) {
        return getFilesForContract(contract).stream()
                .map(ContractFile::getFileUrl)
                .collect(Collectors.toList());
    }
}
