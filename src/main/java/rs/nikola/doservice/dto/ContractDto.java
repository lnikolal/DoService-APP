
package rs.nikola.doservice.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ContractDto {
    private Long id;
    private Long technicianId;
    private Long positionId;
    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<MultipartFile> contractFiles; // ili MultipartFile[] contractFiles;

    public List<MultipartFile> getContractFiles() {
        return contractFiles;
    }
    public void setContractFiles(List<MultipartFile> contractFiles) {
        this.contractFiles = contractFiles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTechnicianId() { return technicianId; }
    public void setTechnicianId(Long technicianId) { this.technicianId = technicianId; }

    public Long getPositionId() { return positionId; }
    public void setPositionId(Long positionId) { this.positionId = positionId; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
