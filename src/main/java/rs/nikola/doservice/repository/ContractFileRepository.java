package rs.nikola.doservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import rs.nikola.doservice.entity.ContractFile;
import rs.nikola.doservice.entity.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractFileRepository extends JpaRepository<ContractFile, Long> {

    List<ContractFile> findByContractAndDeletedAtIsNull(Contract contract);

    Optional<ContractFile> findByIdAndDeletedAtIsNull(Long id);

    List<ContractFile> findByDeletedAtIsNull();

}
