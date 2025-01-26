package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {
    Iterable<Review> findAll();

    Optional<Review> findById(Long id);

//    Iterable<Review> findAllByLocationId(Long locationId);

    Review create(Review review);

    boolean update(Review review);

    boolean delete(Long id);
}
