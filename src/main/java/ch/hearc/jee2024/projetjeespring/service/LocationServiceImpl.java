package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(@Autowired LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location create(Location location) {
        return locationRepository.create(location);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(int id) {
        return locationRepository.findById(id);
    }

    @Override
    public boolean update(int id, Location location) {
        return locationRepository.update(id, location);
    }

    @Override
    public boolean delete(int id) {
        return locationRepository.delete(id);
    }
}
