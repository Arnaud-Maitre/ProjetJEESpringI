package ch.hearc.jee2024.projetjeespring;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.service.LocationService;
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
class LocationControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockitoBean
    private LocationService locationService;

    @Test
    void requestListShouldRespondOkWithEmptyList() throws Exception {
        Mockito.when(locationService.findAll()).thenReturn(new ArrayList<>());

        this.mvc.perform(get("/location"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        Mockito.verify(locationService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestListShouldRespondOkWithLocationList() throws Exception {
        List<Location> locationList = List.of(
                new Location(1L, "Location", "A location", 2.0, -1.0),
                new Location(2L, "Location2", "Another location", -5.2, 2.7)
        );

        Mockito.when(locationService.findAll()).thenReturn(locationList);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(locationList);

        this.mvc.perform(get("/location"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        Mockito.verify(locationService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestShowMissingShouldRespondNotFound() throws Exception {
        Mockito.when(locationService.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        this.mvc.perform(get("/location/1"))
                .andExpect(status().isNotFound());

        Mockito.verify(locationService, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestShowShouldRespondOkWithLocation() throws Exception {
        Location location = new Location(1L, "Test", "A test", 1.0, 1.0);

        Mockito.when(locationService.findById(1)).thenReturn(Optional.of(location));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(get("/location/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(json));

        Mockito.verify(locationService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestCreateShouldRespondCreatedWithLocation() throws Exception {
        Location location = new Location(null, "Test", "A test", 1.0, 1.0);
        Location locationWithId = new Location(1L, "Test", "A test", 1.0, 1.0);

        Mockito.when(locationService.create(Mockito.any(Location.class))).thenReturn(locationWithId);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);
        String jsonWithId = mapper.writeValueAsString(locationWithId);

        this.mvc.perform(post("/location").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonWithId));

        Mockito.verify(locationService, Mockito.times(1)).create(location);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestUpdateMissingShouldRespondNotFound() throws Exception {
        Location location = new Location(1L, "Location", "A location", 1.0, 1.0);

        Mockito.when(locationService.update(Mockito.any(Location.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(put("/location/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(locationService, Mockito.times(1)).update(Mockito.any(Location.class));
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestUpdateShouldRespondOk() throws Exception {
        Location location = new Location(1L, "Location", "A location", 1.0, 1.0);

        Mockito.when(locationService.update(location)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(put("/location/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(locationService, Mockito.times(1)).update(location);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestUpdateWithLocationIdNullShouldRespondOk() throws Exception {
        Location location = new Location(null, "Location", "A location", 1.0, 1.0);
        Location locationWithId = new Location(1L, "Location", "A location", 1.0, 1.0);

        Mockito.when(locationService.update(locationWithId)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(put("/location/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(locationService, Mockito.times(1)).update(locationWithId);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestUpdateWithDifferentIdsShouldRespondBadRequest() throws Exception {
        Location location = new Location(1L, "Location", "A location", 1.0, 1.0);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(put("/location/2").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(locationService);
    }

    @Test
    void requestDeleteMissingShouldRespondNotFound() throws Exception {
        Mockito.when(locationService.delete(Mockito.anyLong())).thenReturn(false);

        this.mvc.perform(delete("/location/1"))
                .andExpect(status().isNotFound());

        Mockito.verify(locationService, Mockito.times(1)).delete(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void requestDeleteShouldRespondNoContent() throws Exception {
        Mockito.when(locationService.delete(1L)).thenReturn(true);

        this.mvc.perform(delete("/location/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(locationService, Mockito.times(1)).delete(1L);
        Mockito.verifyNoMoreInteractions(locationService);
    }
}
