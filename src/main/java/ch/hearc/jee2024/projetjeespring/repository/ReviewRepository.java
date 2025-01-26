package ch.hearc.jee2024.projetjeespring.repository;

import ch.hearc.jee2024.projetjeespring.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
//    Iterable<Review> findAllByLocationId(Long locationId);
}
