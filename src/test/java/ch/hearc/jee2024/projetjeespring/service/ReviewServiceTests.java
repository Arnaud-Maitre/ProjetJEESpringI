package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.model.Review;
import ch.hearc.jee2024.projetjeespring.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewServiceTests {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    @MockitoBean
    private ReviewRepository reviewRepository;

    @Test
    void callFindAllShouldReturnReviewIterable() {
        Iterable<Review> expected = List.of(new Review(1L, 5, "Wow", new Location()));

        Mockito.when(reviewRepository.findAll()).thenReturn(expected);

        Iterable<Review> result = reviewService.findAll();
        Assertions.assertIterableEquals(result, expected);

        Mockito.verify(reviewRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callFindByIdShouldReturnReview() {
        Review expected = new Review(1L, 5, "Wow", new Location());

        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.of(expected));

        Optional<Review> result = reviewService.findById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected, result.get());

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callFindByIdMissingShouldReturnEmpty() {
        Mockito.when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Review> result = reviewService.findById(1L);
        Assertions.assertFalse(result.isPresent());

        Mockito.verify(reviewRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callCreateShouldCallSave() throws Exception {
        Review review = new Review(null, 5, "Wow", new Location());
        Review reviewWithId = new Review(1L, 5, "Wow", new Location());

        Mockito.when(reviewRepository.save(review)).thenReturn(reviewWithId);

        Review result = reviewService.create(review);

        Assertions.assertEquals(reviewWithId, result);

        Mockito.verify(reviewRepository).save(review);
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callCreateWithIdShouldCallSaveWithoutId() {
        Review reviewOriginalId = new Review(42L, 5, "Wow", new Location());
        Review reviewNewId = new Review(1L, 5, "Wow", new Location());

        Mockito.when(reviewRepository.save(Mockito.argThat(review -> review.getId() == null))).thenReturn(reviewNewId);

        Review result = reviewService.create(reviewOriginalId);

        Assertions.assertEquals(reviewNewId, result);

        Mockito.verify(reviewRepository).save(Mockito.argThat(review -> review.getId() == null));
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callUpdateShouldCallSaveAndReturnTrue() {
        Review review = new Review(1L, 5, "Wow", new Location());

        Mockito.when(reviewRepository.existsById(1L)).thenReturn(true);
        Mockito.when(reviewRepository.save(review)).thenReturn(review);

        boolean result = reviewService.update(review);

        Assertions.assertTrue(result);

        Mockito.verify(reviewRepository).existsById(review.getId());
        Mockito.verify(reviewRepository).save(review);
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callUpdateMissingShouldReturnFalse() {
        Review review = new Review(1L, 5, "Wow", new Location());

        Mockito.when(reviewRepository.existsById(1L)).thenReturn(false);

        boolean result = reviewService.update(review);

        Assertions.assertFalse(result);

        Mockito.verify(reviewRepository).existsById(review.getId());
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callDeleteShouldCallDeleteByIdAndReturnTrue() {
        Mockito.when(reviewRepository.existsById(1L)).thenReturn(true);

        boolean result = reviewService.delete(1L);

        Assertions.assertTrue(result);

        Mockito.verify(reviewRepository).existsById(1L);
        Mockito.verify(reviewRepository).deleteById(1L);
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void callDeleteMissingShouldReturnFalse() {
        Mockito.when(reviewRepository.existsById(1L)).thenReturn(false);

        boolean result = reviewService.delete(1L);

        Assertions.assertFalse(result);

        Mockito.verify(reviewRepository).existsById(1L);
        Mockito.verifyNoMoreInteractions(reviewRepository);
    }
}
