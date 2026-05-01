package com.dev.ssc.infrastructure.out.fastapi;

import com.dev.ssc.application.port.out.SpatialEnginePort;
import com.dev.ssc.application.port.out.dto.SpatialEngineRequest;
import com.dev.ssc.core.dto.SpatialResult;
import com.dev.ssc.infrastructure.out.fastapi.dto.NearbyResponse;
import com.dev.ssc.infrastructure.out.fastapi.dto.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// 생성자를 하나하나 쓰는 게 나을까, Bean에 올려두는 게 나을까 생각..
@Component
public class FastApiAdapter implements SpatialEnginePort {

    //private final WebClient webClient = WebClient.create("http://127.0.0.1:8000");
    private final WebClient webClient;

    private static final Logger logger = LogManager.getLogger(FastApiAdapter.class);

//    public FastApiAdapter(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder
//                .baseUrl("http://127.0.0.1:8000")
//                .build();
//    }

    public FastApiAdapter(WebClient.Builder webClientBuilder, @Value("${external.api.fastapi.url}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }


    // 안쪽(Application/Core)은 바깥쪽(Infrastructure)을 절대로 몰라야 한다.

    @Override
    public Mono<SpatialResult> callExternalEngine(SpatialEngineRequest request) {

        logger.info("callExternalEngine");

        return webClient
                .post()
                .uri("/nearby")
                .bodyValue(new SearchRequest(request.lat(), request.lon(), request.k())) // 처리 // new를 붙여서 힙 메모리 실제 공간 할당
                .retrieve()
                .bodyToMono(NearbyResponse.class) // 반환
                .map(NearbyResponse::toDomain);
    }
}
