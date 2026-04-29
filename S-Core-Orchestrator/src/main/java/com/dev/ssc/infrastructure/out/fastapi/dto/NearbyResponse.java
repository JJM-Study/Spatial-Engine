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
            @JsonProperty("lat") Double myLat,
            @JsonProperty("lon") Double myLon

            ) {}

    public record Location(

            @JsonProperty("node_id")
            int nodeId,
            @JsonProperty("distance_km")
            Double distanceKm,
            @JsonProperty("lat")
            Double lat,
            @JsonProperty("lon")
            Double lon
    ) {}

    public SpatialResult toDomain() {

        return new SpatialResult(
                this.myLocation.myLat,
                this.myLocation.myLon,
                this.nearbyLocations.stream()
                        .map(
                        )

                )


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
