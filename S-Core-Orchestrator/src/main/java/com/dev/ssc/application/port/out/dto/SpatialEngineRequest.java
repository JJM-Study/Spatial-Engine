package com.dev.ssc.application.port.out.dto;


public record SpatialEngineRequest(
        Double lon,
        Double lat,
        int k
) {
}
