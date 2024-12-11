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
    void callListShouldReturnEmptyList() throws Exception {
        Mockito.when(locationService.findAll()).thenReturn(new ArrayList<>());

        this.mvc.perform(get("/location"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        Mockito.verify(locationService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callListShouldReturnLocationList() throws Exception {
        List<Location> locationList = List.of(
                new Location(1, "Location", "A location", 2.0, -1.0),
                new Location(2, "Location2", "Another location", -5.2, 2.7)
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
    void callShowMissingShouldReturn404() throws Exception {
        Mockito.when(locationService.findById(Mockito.anyInt())).thenReturn(null);

        this.mvc.perform(get("/location/1"))
                .andExpect(status().isNotFound());

        Mockito.verify(locationService, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callShowShouldReturnLocation() throws Exception {
        Location location = new Location(1, "Test", "A test", 1.0, 1.0);

        Mockito.when(locationService.findById(1)).thenReturn(location);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(get("/location/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(json));

        Mockito.verify(locationService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callCreateShouldReturnLocationWith201() throws Exception {
        Location location = new Location(-35425376, "Test", "A test", 1.0, 1.0);
        Location locationWithId = location.withId(1);

        Mockito.when(locationService.create(Mockito.any(Location.class))).thenReturn(locationWithId);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);
        String jsonWithId = mapper.writeValueAsString(location.withId(1));

        this.mvc.perform(post("/location").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonWithId));

        Mockito.verify(locationService, Mockito.times(1)).create(location);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callUpdateMissingShouldReturn404() throws Exception {
        Location location = new Location(1, "Location", "A location", 1.0, 1.0);

        Mockito.when(locationService.update(Mockito.anyInt(), Mockito.any(Location.class))).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(put("/location/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(locationService, Mockito.times(1)).update(Mockito.anyInt(), Mockito.any(Location.class));
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callUpdateShouldReturn200() throws Exception {
        Location location = new Location(1, "Location", "A location", 1.0, 1.0);

        Mockito.when(locationService.update(1, location)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(location);

        this.mvc.perform(put("/location/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(locationService, Mockito.times(1)).update(1, location);
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callDeleteMissingShouldReturn404() throws Exception {
        Mockito.when(locationService.delete(Mockito.anyInt())).thenReturn(false);

        this.mvc.perform(delete("/location/1"))
                .andExpect(status().isNotFound());

        Mockito.verify(locationService, Mockito.times(1)).delete(Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(locationService);
    }

    @Test
    void callDeleteShouldReturn204() throws Exception {
        Mockito.when(locationService.delete(1)).thenReturn(true);

        this.mvc.perform(delete("/location/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(locationService, Mockito.times(1)).delete(1);
        Mockito.verifyNoMoreInteractions(locationService);
    }
}
