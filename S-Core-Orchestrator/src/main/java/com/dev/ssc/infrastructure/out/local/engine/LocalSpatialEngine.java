package com.dev.ssc.infrastructure.out.local.engine;


import com.dev.ssc.application.port.in.dto.SpatialSearchQuery;
import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.out.fastapi.FastApiAdapter;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import com.dev.ssc.infrastructure.out.local.LocalEngineAdapter;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineRequest;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineResponse;
import reactor.core.publisher.Mono;
import java.util.List;

public class LocalSpatialEngine {


    public Mono<LocalEngineResponse> get_nearby(LocalEngineRequest request) {



        return Mono.just(new LocalEngineResponse(
                        new LocalEngineResponse.MyLocation(request.lat(), request.lon()),
                List.of(new LocalEngineResponse.Location(1, 3235.5, 1.5, 1.5))));

    }
}
