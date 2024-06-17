package com.propertyviewer.SpringApplication.controller;

import com.propertyviewer.SpringApplication.entity.PropertyViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.propertyviewer.SpringApplication.service.PVService;

import java.util.List;

@RestController
@RequestMapping("/propertyviewer")
public class PVController {

    @Autowired
    private PVService propertyService;

    @PostMapping("/add")
    public PropertyViewer addProperty(@RequestBody PropertyViewer property) {
        return propertyService.addProperty(property);
    }

    @GetMapping("/view")
    public List<PropertyViewer> findAll() {
        System.out.println("Hello World");
        return propertyService.findAll();
    }

    @GetMapping("/viewById")
    public PropertyViewer findById(@RequestParam int buildingnumber) {
        return propertyService.findById(buildingnumber);
    }

    @PutMapping("/update")
    public PropertyViewer update(@RequestBody PropertyViewer property) {
        return propertyService.updateProperty(property);
    }

    @DeleteMapping("/deleteById")
    public void delete(@RequestParam int buildingnumber) {
        propertyService.deleteProperty(buildingnumber);
    }

}
