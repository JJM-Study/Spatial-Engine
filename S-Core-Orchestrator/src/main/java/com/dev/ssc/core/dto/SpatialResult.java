package com.dev.ssc.core.dto;


import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;


public record SpatialResult(

        @JsonProperty("center_lon")
        Double centerLon,

        @JsonProperty("center_lat")
        Double centerLat,

        @JsonProperty("nearby_nodes")
        List<NodeInfo> nearbyNodes
) {

        public record NodeInfo (

            @JsonProperty("node_id")
            int nodeId,

            @JsonProperty("distance")
            Double distance,

            @JsonProperty("lon")
            Double lon,


            @JsonProperty("lat")
            Double lat


            ) {}
}