package com.dev.ssc.infrastructure.global.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger Logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExternalEngineException.class)
    public Mono<ResponseEntity<String>> handleExternalEngineError(ExternalEngineException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        String code = e.getErrorCode().getCode();
        String message = e.getErrorCode().getMessage();


        System.out.println("전역 에러 로그: " + message + " | 에러코드: " + code);
        return Mono.just(ResponseEntity
                .status(status)
                .body("엔진 에러 발생 : code {" + code + "}" + "message {" + message + "}"));
    }
}
