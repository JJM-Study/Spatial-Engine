package com.dev.ssc.infrastructure.out.fastapi.dto;


import com.dev.ssc.core.dto.SpatialResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record NearbyResponse (

        @JsonProperty("my_location")
        MyLocation myLocation,
        @JsonProperty("nearby_locations")
        List<Location> nearbyLocations

) {
    public record MyLocation (
            @JsonProperty("lon") Double myLon,
            @JsonProperty("lat") Double myLat

            ) {}

    public record Location(

            @JsonProperty("node_id")
            int nodeId,
            @JsonProperty("distance_km")
            Double distanceKm,
            @JsonProperty("lon")
            Double lon,
            @JsonProperty("lat")
            Double lat
    ) {}

    public SpatialResult toDomain() {

        return new SpatialResult(
                this.myLocation.myLon,
                this.myLocation.myLat,
                this.nearbyLocations.stream()
                        .map(loc -> new SpatialResult.NodeInfo(
                                loc.nodeId(),
                                loc.distanceKm(),
                                loc.lon(),
                                loc.lat()
                                )).toList()
                );
    }
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
