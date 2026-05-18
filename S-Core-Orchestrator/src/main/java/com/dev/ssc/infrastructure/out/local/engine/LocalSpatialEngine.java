package com.dev.ssc.infrastructure.out.local.engine;


import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.out.fastapi.FastApiAdapter;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import reactor.core.publisher.Mono;

public class LocalSpatialEngine {

    public Mono<SpatialResult> get_nearby(SearchRequest request) {



        return
    }
}
