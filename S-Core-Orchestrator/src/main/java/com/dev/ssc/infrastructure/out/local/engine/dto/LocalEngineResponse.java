package com.dev.ssc.infrastructure.out.local.engine.dto;


import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import reactor.core.publisher.Mono;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LocalEngineResponse(
   MyLocation myLocation,

   List<Location> nearbyLocations

) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record MyLocation(
            Double myLon,
            Double myLat

    ) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Location(
        int nodeId,
        Double distanceKm,
        Double lon,

        Double lat
    ) {}

    public SpatialResult toDomain() {

        return new SpatialResult(
                this.myLocation().myLon,
                this.myLocation().myLat,
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
