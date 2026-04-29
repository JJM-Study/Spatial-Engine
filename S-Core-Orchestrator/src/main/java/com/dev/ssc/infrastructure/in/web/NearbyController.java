package com.dev.ssc.infrastructure.in.web;

import com.dev.ssc.application.port.in.SpatialEngineUseCase;
import com.dev.ssc.application.port.in.dto.SpatialSearchQuery;
import com.dev.ssc.core.dto.SpatialResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
// /api/v1/spatial 이냐, /api/spatial/v1 이냐 ..
@RequestMapping("/api/v1/spatial")
public class NearbyController {
    private final SpatialEngineUseCase spatialEngineUseCase;

    private static final Logger logger = LogManager.getLogger(NearbyController.class);

    public NearbyController(SpatialEngineUseCase spatialEngineUseCase) {
        this.spatialEngineUseCase = spatialEngineUseCase;
    }

//    public NearbyController(SpatialEngineUseCase spatialEngineUseCase) {
//        this.spatialEngineUseCase = spatialEngineUseCase;
//    }


    // 굳이 NearbyResponse를, 개별 infrastructure에 dto로 정의할 필요가 있을까?
    // 어차피 core의 dto에 종속될텐데..
    // -> 그렇다면 JsonProperty 의 문제는?
    @PostMapping("/nearby")
    //public Mono<SpatialResult> findNearby(Double lat, Double lon, Integer k) {
    public Mono<SpatialResult> findNearby(@RequestBody SpatialSearchQuery request) {

        logger.info("SpatialSearchQuery :" + request);


        return spatialEngineUseCase.findNearby(request);

    }
}
