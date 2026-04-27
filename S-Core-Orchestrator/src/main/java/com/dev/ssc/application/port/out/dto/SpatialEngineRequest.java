package com.dev.ssc.application.port.out.dto;

public record SpatialEngineRequest(
        Double lat,
        Double lon,
        int k
) {
}
