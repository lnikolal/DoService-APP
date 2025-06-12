package rs.nikola.doservice.service;

import rs.nikola.doservice.entity.Location;
import rs.nikola.doservice.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllActive() {
        return locationRepository.findByDeletedAtIsNull();
    }

    public Location getById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Location not found"));
    }

    public Location save(Location loc) {
        if (locationRepository.existsByNameAndDeletedAtIsNull(loc.getName()))
            throw new IllegalArgumentException("Location already exists!");
        return locationRepository.save(loc);
    }

    public void softDelete(Long id) {
        Location loc = getById(id);
        loc.setDeletedAt(java.time.LocalDateTime.now());
        locationRepository.save(loc);
    }

    public Optional<Location> findByName(String name) {
        return locationRepository.findByNameAndDeletedAtIsNull(name);
    }
}
