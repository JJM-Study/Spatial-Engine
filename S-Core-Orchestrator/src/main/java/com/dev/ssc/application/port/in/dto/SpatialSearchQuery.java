package com.dev.ssc.application.port.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpatialSearchQuery(
        @JsonProperty("lat") Double lat,
        @JsonProperty("lon") Double lon,
        @JsonProperty("k") int k

) {

}
