package com.dev.ssc.infrastructure.out.local.engine.dto;

public record LocalEngineRequest(
        Double lon,
        Double lat,
        Integer k
) { }
