package ch.hearc.jee2024.projetjeespring.repository;

import ch.hearc.jee2024.projetjeespring.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository {
    Location create(Location location);

    List<Location> findAll();

    Location findById(int id);

    boolean update(int id, Location location);

    boolean delete(int id);
}
