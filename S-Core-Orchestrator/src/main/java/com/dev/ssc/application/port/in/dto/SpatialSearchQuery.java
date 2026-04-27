package com.dev.ssc.application.port.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpatialSearchQuery(
        Double lat,
        Double lon,
        int k

) {

}
