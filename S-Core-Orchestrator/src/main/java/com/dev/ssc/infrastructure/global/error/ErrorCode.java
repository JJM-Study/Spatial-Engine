package com.dev.ssc.infrastructure.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // [EXTERNAL ENGINE]
    ENGINE_NOT_FOUND("E001", "외부 엔진을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ENGINE_SERVICE_UNAVAILABLE("E002", "엔진 서비스가 일시적으로 중단되었습니다.", HttpStatus.SERVICE_UNAVAILABLE);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
