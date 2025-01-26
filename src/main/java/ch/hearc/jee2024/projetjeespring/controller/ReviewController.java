package ch.hearc.jee2024.projetjeespring.controller;

import ch.hearc.jee2024.projetjeespring.model.Review;
import ch.hearc.jee2024.projetjeespring.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(@Autowired ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> list() {
        return ResponseEntity.ok(StreamSupport.stream(reviewService.findAll().spliterator(), false).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findById(@PathVariable long id) {
        return reviewService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return ResponseEntity.status(201).body(reviewService.create(review));
    }

    // Currently, when requesting this endpoint with id field responds error 400, for an unknown reason. LocationController works fine
    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable long id, @RequestBody Review review) {
        System.out.println("in update");
        if (review.getId() == null) {
            System.out.println("null id set");
            review.setId(id);
        }
        else if (review.getId() != id) {
            System.out.println("wrong id");
            return ResponseEntity.badRequest().build();
        }
        if (reviewService.update(review)) {
            System.out.println("updated review");
            return ResponseEntity.ok().build();
        }
        System.out.println("missing review");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (reviewService.delete(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
