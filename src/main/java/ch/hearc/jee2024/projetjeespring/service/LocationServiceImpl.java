package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(@Autowired LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location create(Location location) {
        if (location.getId() != null)
            location.setId(null);
        return locationRepository.save(location);
    }

    @Override
    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(long id) {
        return locationRepository.findById(id);
    }

    @Override
    public boolean update(Location location) {
        if (!locationRepository.existsById(location.getId()))
            return false;
        locationRepository.save(location);
        return true;
    }

    @Override
    public boolean delete(long id) {
        if (!locationRepository.existsById(id))
            return false;
        locationRepository.deleteById(id);
        return true;
    }
}
