package rs.nikola.doservice.service;

import rs.nikola.doservice.entity.ServiceType;
import rs.nikola.doservice.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;



@Service
public class ServiceTypeService {
    final private ServiceTypeRepository serviceTypeRepository;
    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public List<ServiceType> getAllActive() {
        return serviceTypeRepository.findByDeletedAtIsNull();
    }

   public ServiceType getById(Long id) {
        return serviceTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Location not found"));

   }

   public ServiceType save(ServiceType serviceType) {
        if (serviceTypeRepository.existsByNameAndDeletedAtIsNull(serviceType.getName()))
            throw new IllegalArgumentException("Service type already exists");
        return serviceTypeRepository.save(serviceType);
   }


   public void softDelete(Long id){
        ServiceType seviceType = getById(id);
        seviceType.setDeletedAt(LocalDateTime.now());
        serviceTypeRepository.save(seviceType);
   }

    public Optional<ServiceType> findByName(String name) {
        return serviceTypeRepository.findByNameAndDeletedAtIsNull(name);
    }


}
