package com.dev.ssc.infrastructure.out.fastapi.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record NearbyResponse (
  Double myLat,
  Double myLon,
  List<Location> nearbyLocations

) {
    record Location(
        int node_id,
        Double distance_km,

        @JsonProperty("lat")
        Double lat,
        @JsonProperty("lon")
        Double lon

    ) {}

 }

//
//public class NearbyResponse {
//    private Location my_location;
//    private List<LocationInfo> nearby_locations;
//
//    public static class Location {
//        private Double lat;
//        private Double lon;
//
//    }
//
//    public static class LocationInfo {
//        private int n_id;
//        private Double distance_km;
//        private Double lat;
//        private Double lon;
//    }
//}
