package ch.hearc.jee2024.projetjeespring;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(@Autowired LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> list() {
        return ResponseEntity.status(200).body(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> show(@PathVariable int id) {
        Location location = locationService.findById(id);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(200).body(location);
    }

    @PostMapping()
    public ResponseEntity<Location> create(@RequestBody Location location) {
        return ResponseEntity.status(201).body(locationService.create(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Location location) {
        if (locationService.update(id, location))
            return ResponseEntity.status(200).build();
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (locationService.delete(id))
            return ResponseEntity.status(204).build();
        return ResponseEntity.status(404).build();
    }
}
