package com.dev.ssc.application.port.in;

import com.dev.ssc.application.port.in.dto.SpatialSearchQuery;
import com.dev.ssc.core.dto.SpatialResult;
import reactor.core.publisher.Mono;

public interface SpatialEngineUseCase {
    Mono<SpatialResult> findNearby(SpatialSearchQuery spatialSearchQuery);

}
