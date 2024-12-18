package ch.hearc.jee2024.projetjeespring.repository;

import ch.hearc.jee2024.projetjeespring.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}
