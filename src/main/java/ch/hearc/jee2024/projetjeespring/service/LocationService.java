package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Location;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LocationService {
    Iterable<Location> findAll();

    Optional<Location> findById(long id);

    Location create(Location location);

    boolean update(Location location);

    boolean delete(long id);
}
