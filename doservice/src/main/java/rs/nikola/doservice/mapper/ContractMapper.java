package rs.nikola.doservice.mapper;

import rs.nikola.doservice.dto.ContractDto;
import rs.nikola.doservice.entity.Contract;
import rs.nikola.doservice.entity.Position;
import rs.nikola.doservice.entity.Technician;

public class ContractMapper {

    public static Contract toEntity(ContractDto dto, Technician technician, Position position) {
        Contract contract = new Contract();
        contract.setId(dto.getId());
        contract.setTechnician(technician);
        contract.setPosition(position);
        contract.setSalary(dto.getSalary());
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
        return contract;
    }

    public static ContractDto toDto(Contract contract) {
        ContractDto dto = new ContractDto();
        dto.setId(contract.getId());
        dto.setTechnicianId(contract.getTechnician().getId());
        dto.setPositionId(contract.getPosition().getId());
        dto.setSalary(contract.getSalary());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        return dto;
    }
}
