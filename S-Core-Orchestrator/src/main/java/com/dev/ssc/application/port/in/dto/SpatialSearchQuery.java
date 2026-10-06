package com.dev.ssc.application.port.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpatialSearchQuery(
        @JsonProperty("lon") Double lon,

        @JsonProperty("lat") Double lat,

        @JsonProperty("k") int k

) {

}
