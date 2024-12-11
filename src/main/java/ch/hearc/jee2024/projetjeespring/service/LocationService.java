package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    List<Location> findAll();
    Location findById(int id);
    Location create(Location location);
    boolean update(int id, Location location);
    boolean delete(int id);
}
