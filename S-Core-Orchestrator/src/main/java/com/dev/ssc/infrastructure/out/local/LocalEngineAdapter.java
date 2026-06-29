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
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class LocalEngineAdapter implements SpatialEnginePort {

    private static final Logger logger = LogManager.getLogger(LocalEngineAdapter.class);

    // @Authorized 자체가 객체 생성 이후 주입이고, 동시성 문제 등도 생각해야하니, final로 지정 할 필요 있을 것 같다.
    private final LocalSpatialEngine localSpatialEngine;

//    public LocalEngineAdapter() {
//
//    }

    @Override
    public Mono<SpatialResult> execute (SpatialEngineRequest request) {

        logger.info("LocalEngine executed");
        return localSpatialEngine.get_nearby(
                        new LocalEngineRequest(request.lat(), request.lon(), request.k())
                )
                .map(LocalEngineResponse::toDomain)
                .onErrorMap(Throwable.class, e -> {
                            if (e instanceof WebClientResponseException wre) {
                                logger.info("An error occurred during engine execution: {} ", e);
                                return new ExternalEngineException(ErrorCode.ENGINE_SERVICE_UNAVAILABLE, wre, wre.getStatusCode().value(), wre.getResponseBodyAsString());
                            }
//                Mono.just( new SpatialResult(request.lat(), request.lon(), new ArrayList<>()));
                            logger.info("Runtime Exception: {} ", e);
                            return new ExternalEngineException(ErrorCode.ENGINE_SERVICE_UNAVAILABLE, e);
                        }
                );
        }
    }
