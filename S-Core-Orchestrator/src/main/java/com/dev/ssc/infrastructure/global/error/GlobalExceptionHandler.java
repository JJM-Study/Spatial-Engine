package com.dev.ssc.infrastructure.global.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExternalEngineException.class)
    public Mono<ResponseEntity<String>> handleExternalEngineError(ExternalEngineException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        String code = e.getErrorCode().getCode();
        String message = e.getErrorCode().getMessage();
        int rawStatusCode = e.getRawStatusCode();
        String rawResponseBody = e.getRawResponseBody();

        if (e.getCause() != null) {
            Throwable realCause = e.getCause();

            String causeClassName = realCause.getClass().getSimpleName();
            String causeMessage = realCause.getMessage();

            logger.error("Failed to call Internal API. CauseException: {}, CauseMessage: {}", causeClassName, causeMessage);
        }


        logger.info("전역 에러 로그: " + message + " | 에러코드: " + code);



        logger.info("Failed to call external API. Response Status: {}, Body: {}", rawStatusCode, rawResponseBody);

        return Mono.just(ResponseEntity
                .status(status)
                .body("엔진 에러 발생 : code {" + code + "}" + "message {" + message + "}"));
    }


}
