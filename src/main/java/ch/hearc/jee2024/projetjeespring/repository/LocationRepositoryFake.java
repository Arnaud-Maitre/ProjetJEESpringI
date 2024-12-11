package ch.hearc.jee2024.projetjeespring.repository;

import ch.hearc.jee2024.projetjeespring.model.Location;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocationRepositoryFake implements LocationRepository {
    Map<Integer, Location> fakeDb;
    int lastId;

    public LocationRepositoryFake() {
        this.fakeDb = new HashMap<>();
        this.lastId = 0;
    }

    @Override
    public List<Location> findAll() {
        return fakeDb.values().stream().toList();
    }

    @Override
    public Location findById(int id) {
        return fakeDb.get(id);
    }

    @Override
    public Location create(Location location) {
        Location locationWithId = location.withId(++lastId);
        fakeDb.put(lastId, locationWithId);
        return locationWithId;
    }

    @Override
    public boolean update(int id, Location location) {
        if (!fakeDb.containsKey(id))
            return false;
        fakeDb.put(id, location.withId(id));
        return true;
    }

    @Override
    public boolean delete(int id) {
        return fakeDb.remove(id) != null;
    }
}
