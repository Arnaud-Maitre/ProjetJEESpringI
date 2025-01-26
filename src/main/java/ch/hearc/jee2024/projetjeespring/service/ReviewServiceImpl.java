package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Review;
import ch.hearc.jee2024.projetjeespring.repository.LocationRepository;
import ch.hearc.jee2024.projetjeespring.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(@Autowired ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Iterable<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

//    @Override
//    public Iterable<Review> findAllByLocationId(Long locationId) {
//        return reviewRepository.findAllByLocationId(locationId).stream().map(ReviewEntity::toReview).toList();
//    }

    @Override
    public Review create(Review review) {
        review.setId(null);
        System.out.println(review);
        return reviewRepository.save(review);
    }

    @Override
    public boolean update(Review review) {
        if (!reviewRepository.existsById(review.getId()))
            return false;
        reviewRepository.save(review);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if (!reviewRepository.existsById(id))
            return false;
        reviewRepository.deleteById(id);
        return true;
    }
}
