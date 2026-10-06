package com.dev.ssc.infrastructure.out.fastapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

//public class SearchRequest {
public record SearchRequest (

        @JsonProperty("my_lon") Double myLon,
        @JsonProperty("my_lat") Double myLat,
        Integer k

) {}
