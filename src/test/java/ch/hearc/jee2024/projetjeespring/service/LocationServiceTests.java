package ch.hearc.jee2024.projetjeespring.service;

import ch.hearc.jee2024.projetjeespring.model.Location;
import ch.hearc.jee2024.projetjeespring.repository.LocationRepository;
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
public class LocationServiceTests {

    @Autowired
    private LocationService locationService;

    @Autowired
    @MockitoBean
    private LocationRepository locationRepository;

    @Test
    void callFindAllShouldReturnLocationIterable() {
        Iterable<Location> expected = List.of(new Location(1L, "Test", "A Test", 23.23, 42.42, List.of()));

        Mockito.when(locationRepository.findAll()).thenReturn(expected);

        Iterable<Location> result = locationService.findAll();
        Assertions.assertIterableEquals(result, expected);

        Mockito.verify(locationRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callFindByIdShouldReturnLocation() {
        Location expected = new Location(1L, "Test", "A Test", 23.23, 42.42, List.of());

        Mockito.when(locationRepository.findById(1L)).thenReturn(Optional.of(expected));

        Optional<Location> result = locationService.findById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected, result.get());

        Mockito.verify(locationRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callFindByIdMissingShouldReturnEmpty() {
        Mockito.when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Location> result = locationService.findById(1L);
        Assertions.assertFalse(result.isPresent());

        Mockito.verify(locationRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callCreateShouldCallSave() {
        Location location = new Location(null, "Test", "A Test", 4.246, 68.54, List.of());
        Location locationWithId = new Location(1L, "Test", "A Test", 4.246, 68.54, List.of());

        Mockito.when(locationRepository.save(location)).thenReturn(locationWithId);

        Location result = locationService.create(location);

        Assertions.assertEquals(locationWithId, result);

        Mockito.verify(locationRepository).save(location);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callCreateWithIdShouldCallSaveWithoutId() {
        Location locationOriginalId = new Location(42L, "Test", "A Test", 4.246, 68.54, List.of());
        Location locationNoId = new Location(null, "Test", "A Test", 4.246, 68.54, List.of());
        Location locationNewId = new Location(1L, "Test", "A Test", 4.246, 68.54, List.of());

        Mockito.when(locationRepository.save(locationNoId)).thenReturn(locationNewId);

        Location result = locationService.create(locationOriginalId);

        Assertions.assertEquals(locationNewId, result);

        Mockito.verify(locationRepository).save(locationNoId);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callUpdateShouldCallSaveAndReturnTrue() {
        Location location = new Location(1L, "Test", "A Test", 4.246, 68.54, List.of());

        Mockito.when(locationRepository.existsById(1L)).thenReturn(true);
        Mockito.when(locationRepository.save(location)).thenReturn(location);

        boolean result = locationService.update(location);

        Assertions.assertTrue(result);

        Mockito.verify(locationRepository).existsById(location.getId());
        Mockito.verify(locationRepository).save(location);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callUpdateMissingShouldReturnFalse() {
        Location location = new Location(1L, "Test", "A Test", 4.246, 68.54, List.of());

        Mockito.when(locationRepository.existsById(1L)).thenReturn(false);

        boolean result = locationService.update(location);

        Assertions.assertFalse(result);

        Mockito.verify(locationRepository).existsById(location.getId());
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callDeleteShouldCallDeleteByIdAndReturnTrue() {
        Mockito.when(locationRepository.existsById(1L)).thenReturn(true);

        boolean result = locationService.delete(1L);

        Assertions.assertTrue(result);

        Mockito.verify(locationRepository).existsById(1L);
        Mockito.verify(locationRepository).deleteById(1L);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }

    @Test
    void callDeleteMissingShouldReturnFalse() {
        Mockito.when(locationRepository.existsById(1L)).thenReturn(false);

        boolean result = locationService.delete(1L);

        Assertions.assertFalse(result);

        Mockito.verify(locationRepository).existsById(1L);
        Mockito.verifyNoMoreInteractions(locationRepository);
    }
}
