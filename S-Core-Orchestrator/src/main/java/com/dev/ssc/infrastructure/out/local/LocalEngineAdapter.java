package com.dev.ssc.infrastructure.out.local;


import com.dev.ssc.application.port.out.SpatialEnginePort;
import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component @Order(2)
public class LocalEngineAdapter implements SpatialEnginePort {

    public LocalEngineAdapter() {

    }

    @Override
    public Mono<SpatialResult> execute (SpatialEngineRequest request) {


        return Mono.just( new SpatialResult(request.lat(), request.lon(), new ArrayList<>()));
    }

}
