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
            Double myLat,
            Double myLon
    ) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Location(
        int nodeId,
        Double distanceKm,
        Double lat,
        Double lon
    ) {}

    public SpatialResult toDomain() {

        return new SpatialResult(
                this.myLocation().myLat,
                this.myLocation().myLon,
                this.nearbyLocations.stream()
                        .map(loc -> new SpatialResult.NodeInfo(
                                        loc.nodeId(),
                                        loc.distanceKm(),
                                        loc.lat(),
                                        loc.lon()
                        )).toList()
        );
    }


}
