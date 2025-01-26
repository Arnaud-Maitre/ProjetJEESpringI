package ch.hearc.jee2024.projetjeespring.controller;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.model.Review;
import ch.hearc.jee2024.projetjeespring.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockitoBean
    private ReviewService reviewService;

    @Test
    void requestListShouldRespondOkWithEmptyList() throws Exception {
        Mockito.when(reviewService.findAll()).thenReturn(new ArrayList<>());

        this.mvc.perform(get("/review"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        Mockito.verify(reviewService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestListShouldRespondOkWithReviewList() throws Exception {
        List<Review> reviewList = List.of(
                new Review(1L, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null)),
                new Review(2L, 4, "Cool", new Location(1L, "Test", "A Test", 4.246, 68.54, null))
        );

        Mockito.when(reviewService.findAll()).thenReturn(reviewList);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(reviewList);

        this.mvc.perform(get("/review"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        Mockito.verify(reviewService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestShowMissingShouldRespondNotFound() throws Exception {
        Mockito.when(reviewService.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        this.mvc.perform(get("/review/1"))
                .andExpect(status().isNotFound());

        Mockito.verify(reviewService, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestShowShouldRespondOkWithReview() throws Exception {
        Review review = new Review(1L, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));

        Mockito.when(reviewService.findById(1L)).thenReturn(Optional.of(review));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(review);

        this.mvc.perform(get("/review/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(json));

        Mockito.verify(reviewService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestCreateShouldRespondCreatedWithReview() throws Exception {
        Review review = new Review(null, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));
        Review reviewWithId = new Review(1L, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));

        Mockito.when(reviewService.create(Mockito.any(Review.class))).thenReturn(reviewWithId);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(review);
        String jsonWithId = mapper.writeValueAsString(reviewWithId);

        this.mvc.perform(post("/review").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonWithId));

        Mockito.verify(reviewService, Mockito.times(1)).create(review);
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestUpdateMissingShouldRespondNotFound() throws Exception {
        Review review = new Review(1L, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));

        Mockito.when(reviewService.update(Mockito.any(Review.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(review);

        this.mvc.perform(put("/review/1").content("{\"rating\":5,\"comment\":\"Wow\",\"location\":{\"id\":1}}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(reviewService, Mockito.times(1)).update(Mockito.any(Review.class));
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestUpdateShouldRespondOk() throws Exception {
        Review review = new Review(1L, 5, "Wow", new Location());
        review.getLocation().setId(1L);

        Mockito.when(reviewService.update(review)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(review);

        this.mvc.perform(put("/review/1").content("{\"rating\":5,\"comment\":\"Wow\",\"location\":{\"id\":1}}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(reviewService, Mockito.times(1)).update(Mockito.argThat(a -> {System.out.println(a);return true;}));
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestUpdateWithReviewIdNullShouldRespondOk() throws Exception {
        Review review = new Review(null, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));
        Review reviewWithId = new Review(1L, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));

        Mockito.when(reviewService.update(reviewWithId)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(review);

        this.mvc.perform(put("/review/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(reviewService, Mockito.times(1)).update(reviewWithId);
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestUpdateWithDifferentIdsShouldRespondBadRequest() throws Exception {
        Review review = new Review(1L, 5, "Wow", new Location(1L, "Test", "A Test", 4.246, 68.54, null));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(review);

        this.mvc.perform(put("/review/2").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(reviewService);
    }

    @Test
    void requestDeleteMissingShouldRespondNotFound() throws Exception {
        Mockito.when(reviewService.delete(Mockito.anyLong())).thenReturn(false);

        this.mvc.perform(delete("/review/1"))
                .andExpect(status().isNotFound());

        Mockito.verify(reviewService, Mockito.times(1)).delete(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(reviewService);
    }

    @Test
    void requestDeleteShouldRespondNoContent() throws Exception {
        Mockito.when(reviewService.delete(1L)).thenReturn(true);

        this.mvc.perform(delete("/review/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(reviewService, Mockito.times(1)).delete(1L);
        Mockito.verifyNoMoreInteractions(reviewService);
    }
}
