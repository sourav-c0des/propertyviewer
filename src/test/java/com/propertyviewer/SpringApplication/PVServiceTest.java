package com.propertyviewer.SpringApplication;

import com.propertyviewer.SpringApplication.entity.PropertyViewer;
import com.propertyviewer.SpringApplication.repository.PVRepository;
import com.propertyviewer.SpringApplication.service.GeoapifyService;
import com.propertyviewer.SpringApplication.service.PVService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PVServiceTest {

    @Mock
    private PVRepository propertyRepository;

    @Mock
    private GeoapifyService geoapifyService;

    @InjectMocks
    private PVService pvService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Positive Test Case for addProperty
    @Test
    public void testAddProperty_Success() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);
        property.setBuildingname("BuildingName");
        property.setStreet("Street");
        property.setPostcode("Postcode");
        property.setCity("City");
        property.setCountry("Country");
        property.setDescription("Description");

        when(geoapifyService.getCoordinates(anyString())).thenReturn(new double[]{10.0, 20.0});
        when(propertyRepository.save(any(PropertyViewer.class))).thenReturn(property);

        PropertyViewer result = pvService.addProperty(property);

        assertNotNull(result);
        assertEquals(10.0, result.getLatitude());
        assertEquals(20.0, result.getLongitude());
        verify(propertyRepository, times(1)).save(property);
    }

    // Negative Test Case for addProperty
    @Test
    public void testAddProperty_GeoapifyServiceFails() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);
        property.setBuildingname("BuildingName");
        property.setStreet("Street");
        property.setPostcode("Postcode");
        property.setCity("City");
        property.setCountry("Country");
        property.setDescription("Description");

        when(geoapifyService.getCoordinates(anyString())).thenThrow(new RuntimeException("Failed to get coordinates"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pvService.addProperty(property);
        });

        assertEquals("Failed to get coordinates", exception.getMessage());
        verify(propertyRepository, never()).save(any(PropertyViewer.class));
    }

    // Positive Test Case for findById
    @Test
    public void testFindById_Success() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);

        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));

        PropertyViewer result = pvService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getBuildingnumber());
        verify(propertyRepository, times(1)).findById(1);
    }

    // Negative Test Case for findById
    @Test
    public void testFindById_PropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pvService.findById(1);
        });

        assertEquals("Property not found", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1);
    }

    // Positive Test Case for findAll
    @Test
    public void testFindAll_Success() {
        PropertyViewer property1 = new PropertyViewer();
        PropertyViewer property2 = new PropertyViewer();

        when(propertyRepository.findAll()).thenReturn(Arrays.asList(property1, property2));

        List<PropertyViewer> result = pvService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(propertyRepository, times(1)).findAll();
    }

    // Negative Test Case for updateProperty
    @Test
    public void testUpdateProperty_PropertyNotFound() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);

        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pvService.updateProperty(property);
        });

        assertEquals("Property not found", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1);
        verify(propertyRepository, never()).save(any(PropertyViewer.class));
    }

    // Positive Test Case for deleteProperty
    @Test
    public void testDeleteProperty_Success() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);

        when(propertyRepository.findById(1)).thenReturn(Optional.of(property));
        doNothing().when(propertyRepository).delete(property);

        pvService.deleteProperty(1);

        verify(propertyRepository, times(1)).findById(1);
        verify(propertyRepository, times(1)).delete(property);
    }

    // Negative Test Case for deleteProperty
    @Test
    public void testDeleteProperty_PropertyNotFound() {
        when(propertyRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pvService.deleteProperty(1);
        });

        assertEquals("Property not found", exception.getMessage());
        verify(propertyRepository, times(1)).findById(1);
        verify(propertyRepository, never()).delete(any(PropertyViewer.class));
    }
}
