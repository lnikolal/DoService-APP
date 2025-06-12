package rs.nikola.doservice.service;


import org.springframework.stereotype.Service;
import rs.nikola.doservice.entity.Position;
import rs.nikola.doservice.repository.PositionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class PositionService {
    final PositionRepository positionRepository;
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllActive() {
        return positionRepository.findByDeletedAtIsNull();
    }

    public Position getById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Position not found"));
    }

    public Position save(Position position) {
        if(positionRepository.existsByNameAndDeletedAtIsNull(position.getName()))
            throw new IllegalArgumentException("Position already exists");
        return positionRepository.save(position);
    }

    public void softDelete(Long id) {
        Position position = getById(id);
        position.setDeletedAt(LocalDateTime.now());
        positionRepository.save(position);
    }

    public Optional<Position> getByName(String name) {
        return positionRepository.findByNameAndDeletedAtIsNull(name);
    }

}
