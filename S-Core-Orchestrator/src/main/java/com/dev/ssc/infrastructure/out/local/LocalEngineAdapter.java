package com.dev.ssc.infrastructure.out.local;


import com.dev.ssc.application.port.in.dto.SpatialSearchQuery;
import com.dev.ssc.application.port.out.SpatialEnginePort;
import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.global.error.ErrorCode;
import com.dev.ssc.infrastructure.global.error.ExternalEngineException;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import com.dev.ssc.infrastructure.out.local.engine.LocalSpatialEngine;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineRequest;
import com.dev.ssc.infrastructure.out.local.engine.dto.LocalEngineResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component @Order(2)
public class LocalEngineAdapter implements SpatialEnginePort {

    private static final Logger logger = LogManager.getLogger(LocalEngineAdapter.class);

    private LocalSpatialEngine localSpatialEngine;

    public LocalEngineAdapter() {

    }

    @Override
    public Mono<SpatialResult> execute (SpatialEngineRequest request) {

        logger.info("LocalEngine executed");
        return localSpatialEngine.get_nearby(
                new LocalEngineRequest(request.lon(), request.lat(), request.k())
                )
                .map(LocalEngineResponse::toDomain)
                .onErrorMap(WebClientResponseException.class, e -> {
                    logger.info("test");
                    return new ExternalEngineException(ErrorCode.ENGINE_SERVICE_UNAVAILABLE, e, e.getStatusCode().value(), e.getResponseBodyAsString());});
//                Mono.just( new SpatialResult(request.lat(), request.lon(), new ArrayList<>()));
    }

}
