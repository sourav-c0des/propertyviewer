package com.propertyviewer.SpringApplication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class GeoapifyService {

    @Value("${geoapify.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeoapifyService() {
        this.restTemplate = new RestTemplate();
    }

    public double[] getCoordinates(String address) {
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.geoapify.com/v1/geocode/search")
                .queryParam("text", address)
                .queryParam("apiKey", apiKey)
                .toUriString();

        GeoapifyResponse response = restTemplate.getForObject(uri, GeoapifyResponse.class);
        if (response != null && !response.getFeatures().isEmpty()) {
            double lat = response.getFeatures().get(0).getGeometry().getCoordinates().get(1);
            double lon = response.getFeatures().get(0).getGeometry().getCoordinates().get(0);
            return new double[]{lat, lon};
        }
        throw new RuntimeException("Failed to get coordinates");
    }

    private static class GeoapifyResponse {
        private List<Feature> features;

        // Getters and setters

        public List<Feature> getFeatures() {
            return features;
        }

        public void setFeatures(List<Feature> features) {
            this.features = features;
        }

        private static class Feature {
            private Geometry geometry;

            // Getters and setters

            public Geometry getGeometry() {
                return geometry;
            }

            public void setGeometry(Geometry geometry) {
                this.geometry = geometry;
            }

            private static class Geometry {
                private List<Double> coordinates;

                // Getters and setters

                public List<Double> getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(List<Double> coordinates) {
                    this.coordinates = coordinates;
                }
            }
        }
    }
}
