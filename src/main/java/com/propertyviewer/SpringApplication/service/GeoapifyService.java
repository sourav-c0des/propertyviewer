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
        String encodedAddress = UriComponentsBuilder.fromUriString(address).toUriString();
        String uri = UriComponentsBuilder.fromHttpUrl("https://api.geoapify.com/v1/geocode/search")
                .queryParam("text", encodedAddress)
                .queryParam("apiKey", apiKey)
                .toUriString();

        GeoapifyResponse response = restTemplate.getForObject(uri, GeoapifyResponse.class);
        if (response != null && !response.getFeatures().isEmpty()) {
            double lat = response.getFeatures().get(0).getProperties().getLat();
            double lon = response.getFeatures().get(0).getProperties().getLon();
            return new double[]{lat, lon};
        }
        throw new RuntimeException("Failed to get coordinates");
    }

     public static class GeoapifyResponse {
        private List<Feature> features;

        public List<Feature> getFeatures() {
            return features;
        }

        public void setFeatures(List<Feature> features) {
            this.features = features;
        }

         public static class Feature {
            private Properties properties;

            public Properties getProperties() {
                return properties;
            }

            public void setProperties(Properties properties) {
                this.properties = properties;
            }
        }

         public static class Properties {
            private double lat;
            private double lon;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }
    }
}
