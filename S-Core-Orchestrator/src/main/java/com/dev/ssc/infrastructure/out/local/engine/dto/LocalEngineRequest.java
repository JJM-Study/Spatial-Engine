package com.dev.ssc.infrastructure.out.local.engine.dto;

public record LocalEngineRequest(
        Double lat,
        Double lon,
        Integer k
) { }
