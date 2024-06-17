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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PVServiceTest {

    @Mock
    private PVRepository propertyRepository;

    @Mock
    private GeoapifyService geoapifyService; // Mocking the GeoapifyService

    @InjectMocks
    private PVService propertyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProperty() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);
        property.setBuildingname("Test Building");
        property.setStreet("Test Street");
        property.setPostcode("12345");
        property.setCity("Test City");
        property.setCountry("Test Country");
        property.setDescription("Test Description");

        when(geoapifyService.getCoordinates(any(String.class))).thenReturn(new double[]{1.0, 2.0});

        when(propertyRepository.save(any(PropertyViewer.class))).thenReturn(property);

        PropertyViewer savedProperty = propertyService.addProperty(property);

        verify(propertyRepository, times(1)).save(any(PropertyViewer.class));

        assert savedProperty != null;
        assert savedProperty.getBuildingnumber() == 1;
        assert savedProperty.getBuildingname().equals("Test Building");
    }

    @Test
    public void testFindById() {
        PropertyViewer property = new PropertyViewer();
        property.setBuildingnumber(1);
        property.setBuildingname("Test Building");
        property.setStreet("Test Street");
        property.setPostcode("12345");
        property.setCity("Test City");
        property.setCountry("Test Country");
        property.setDescription("Test Description");

        when(propertyRepository.findById(1)).thenReturn(java.util.Optional.of(property));
        when(propertyRepository.findById(2)).thenReturn(java.util.Optional.empty());

        PropertyViewer foundProperty = propertyService.findById(1);

        verify(propertyRepository, times(1)).findById(1);

        assert foundProperty != null;
        assert foundProperty.getBuildingnumber() == 1;
        assert foundProperty.getBuildingname().equals("Test Building");

        assertThrows(RuntimeException.class, () -> propertyService.findById(2));
    }
}
