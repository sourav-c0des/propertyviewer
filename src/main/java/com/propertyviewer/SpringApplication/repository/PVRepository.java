package com.propertyviewer.SpringApplication.repository;

import com.propertyviewer.SpringApplication.entity.PropertyViewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PVRepository extends JpaRepository<PropertyViewer,Integer> {
//    PropertyViewer save(PropertyViewer property);
//
//    Optional<PropertyViewer> findById(int buildingnumber);
//
//    List<PropertyViewer> findAll();
//
//    void delete(PropertyViewer propertyViewer);
}
