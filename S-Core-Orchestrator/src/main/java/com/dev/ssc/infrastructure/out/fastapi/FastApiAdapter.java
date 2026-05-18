package com.dev.ssc.infrastructure.out.fastapi;

import com.dev.ssc.infrastructure.global.error.ErrorCode;
import com.dev.ssc.infrastructure.global.error.ExternalEngineException;
import com.dev.ssc.infrastructure.global.error.GlobalExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@Component @Order(1)
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
    public Mono<SpatialResult> execute(SpatialEngineRequest request) {

        logger.info("callExternalEngine");

        //try { ... }  = doOnSubscribe()요청 시작 직전 상태 확인
        // Success (결과 반환) = doOnNext()데이터가 정상적으로 도착했을 때의 로그
        // catch (Exception e) = doOnError()에러 발생 시 로그 (원본 에러 유지)
        // finally { ... } = doFinally()성공/실패 여부와 상관없이 종료 시 실행

        // JSON 정보도 출력되도록 하자.

        // 2026/05/15 if-else 등으로 내외부 엔진을 나눌 경우, 코드 복잡도 증가 가능성..
        return webClient
                .post()
                .uri("/nearby")
                .bodyValue(new SearchRequest(request.lat(), request.lon(), request.k())) // 처리 // new를 붙여서 힙 메모리 실제 공간 할당
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        // 발생한 상태 코드를 에러 메시지로 변환하여 전송
                        response.createException().flatMap(Mono::error)
                )
                .bodyToMono(NearbyResponse.class) // 반환
                .map(NearbyResponse::toDomain)
                // 역직렬화 자체는, 라이브러리 자체적으로 객체 생성 수행함을 감안
                .doOnNext(e -> logger.info("callExternalEngine response : {}", e))
                .doOnError(e -> logger.error("callExternalEngine error :", e))
                .onErrorMap(e -> new ExternalEngineException(ErrorCode.ENGINE_SERVICE_UNAVAILABLE))
                ;

    }
}
