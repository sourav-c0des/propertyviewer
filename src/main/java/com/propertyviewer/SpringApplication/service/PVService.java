package com.propertyviewer.SpringApplication.service;

import com.propertyviewer.SpringApplication.entity.PropertyViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.propertyviewer.SpringApplication.repository.PVRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PVService {

    @Autowired
    private PVRepository propertyRepository;

    @Autowired
    private GeoapifyService geoapifyService;

    public PropertyViewer addProperty(PropertyViewer property) {
        String address = property.getStreet() + ", " + property.getCity() + ", " + property.getCountry();
        double[] coords = geoapifyService.getCoordinates(address);
        property.setLatitude(coords[0]);
        property.setLongitude(coords[1]);
        return propertyRepository.save(property);
    }

    public PropertyViewer findById(int buildingnumber) {
        Optional<PropertyViewer> property = propertyRepository.findById(buildingnumber);

        if (property.isEmpty()) {
            throw new RuntimeException("Not found");
        }

        return property.get();
    }


    public List<PropertyViewer> findAll() {
        return propertyRepository.findAll();
    }

    public PropertyViewer updateProperty(PropertyViewer property) {
        Optional<PropertyViewer> dbuser = propertyRepository.findById(property.getBuildingnumber());

        if (dbuser.isEmpty()) {
            throw new RuntimeException("Not found");
        }

        PropertyViewer existingUser = dbuser.get();
        existingUser.setBuildingname(property.getBuildingname());
        existingUser.setStreet(property.getStreet());
        existingUser.setPostcode(property.getPostcode());
        existingUser.setCity(property.getCity());
        existingUser.setCountry(property.getCountry());
        existingUser.setDescription(property.getDescription());

        String address = property.getPostcode();
        double[] coords = geoapifyService.getCoordinates(address);
        existingUser.setLatitude(coords[0]);
        existingUser.setLongitude(coords[1]);


        return propertyRepository.save(existingUser);
    }

    public void deleteProperty(int buildingnumber) {
        Optional<PropertyViewer> dbuser = propertyRepository.findById(buildingnumber);

        if (dbuser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        propertyRepository.delete(dbuser.get());
    }

}
