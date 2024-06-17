package com.propertyviewer.SpringApplication.service;

import com.propertyviewer.SpringApplication.entity.PropertyViewer;
import com.propertyviewer.SpringApplication.repository.PVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PVService {

    @Autowired
    private PVRepository propertyRepository;

    @Autowired
    private GeoapifyService geoapifyService;

    public PropertyViewer addProperty(PropertyViewer property) {
        String address = property.getBuildingnumber() + ", " + property.getBuildingname() + ", " +
                property.getStreet() + ", " + property.getPostcode() + ", " +
                property.getCity() + ", " + property.getCountry();

        double[] coords = geoapifyService.getCoordinates(address);
        property.setLatitude(coords[0]);
        property.setLongitude(coords[1]);

        return propertyRepository.save(property);
    }

    public PropertyViewer findById(int buildingnumber) {
        Optional<PropertyViewer> property = propertyRepository.findById(buildingnumber);

        if (property.isEmpty()) {
            throw new RuntimeException("Property not found");
        }

        return property.get();
    }

    public List<PropertyViewer> findAll() {
        return propertyRepository.findAll();
    }

    public PropertyViewer updateProperty(PropertyViewer property) {
        Optional<PropertyViewer> dbProperty = propertyRepository.findById(property.getBuildingnumber());

        if (dbProperty.isEmpty()) {
            throw new RuntimeException("Property not found");
        }

        PropertyViewer existingProperty = dbProperty.get();
        existingProperty.setBuildingname(property.getBuildingname());
        existingProperty.setStreet(property.getStreet());
        existingProperty.setPostcode(property.getPostcode());
        existingProperty.setCity(property.getCity());
        existingProperty.setCountry(property.getCountry());
        existingProperty.setDescription(property.getDescription());

        double[] coords = geoapifyService.getCoordinates(existingProperty.getBuildingnumber() + ", " +
                existingProperty.getBuildingname() + ", " +
                existingProperty.getStreet() + ", " +
                existingProperty.getPostcode() + ", " +
                existingProperty.getCity() + ", " +
                existingProperty.getCountry());

        existingProperty.setLatitude(coords[0]);
        existingProperty.setLongitude(coords[1]);

        return propertyRepository.save(existingProperty);
    }

    public void deleteProperty(int buildingnumber) {
        Optional<PropertyViewer> property = propertyRepository.findById(buildingnumber);

        if (property.isEmpty()) {
            throw new RuntimeException("Property not found");
        }

        propertyRepository.delete(property.get());
    }
}
