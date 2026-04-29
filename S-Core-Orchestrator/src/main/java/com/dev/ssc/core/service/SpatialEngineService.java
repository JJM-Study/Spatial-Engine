package com.dev.ssc.core.service;

import com.dev.ssc.application.port.in.SpatialEngineUseCase;
import com.dev.ssc.application.port.in.dto.SpatialSearchQuery;
import com.dev.ssc.application.port.out.SpatialEnginePort;
import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SpatialEngineService implements SpatialEngineUseCase {

    private final SpatialEnginePort spatialEnginePort;

//    SpatialEngineService(SpatialEnginePort spatialEnginePort) {
//        this.spatialEnginePort = spatialEnginePort;
//    }

    private static final Logger logger = LogManager.getLogger(SpatialEngineService.class);

    @Override
    public Mono<SpatialResult> findNearby(SpatialSearchQuery query) {

        logger.info("SpatialEngineService Query : " + query);

        return spatialEnginePort.callExternalEngine(new SpatialEngineRequest(query.lat(), query.lon(), query.k()));
    }
}


//@Service
//public class SpatialEngineService {
//    private final WebClient webClient = WebClient.create("http://127.0.0.1:8000");
//
//
//    // 의존성을 줄이고, 엔진이 파이썬에서 다른 것으로 바뀔 수도 있다는 것에 대비한 추상화
//    public Mono<NearbyResponse> findNearby(Double lan, Double lon, Integer k) {
//
////        SearchRequest request = new SearchRequest(lan, lon, k);
////
////        return webClient
////                .post()
////                .uri("/nearby")
////                .bodyValue(request)
////                .retrieve()
////                .bodyToMono(NearbyResponse.class);
//
//        return
//    }
//
//
//    public Mono<NearbyResponse> callExternalEngine () {
//
//        return
//                request.myLon()
//                request.myLat().
//
//    }
//
//}
