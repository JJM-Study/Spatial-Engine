package com.dev.ssc.infrastructure.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;


// 객체는 한 번 생성딜 시 그 상태가 변하지 않아야 한다. Setter X
@Getter
public class ExternalEngineException extends RuntimeException {

//    private final int errorCode;
//
//    private final HttpStatus status;

    private final ErrorCode errorCode;


    // errorCode를 통해서, 외부 엔진 에러인지, 비즈니스 에러인지 등 구분.
    public ExternalEngineException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


//    public ExternalEngineException(String errorCode, String message, HttpStatus status) {
//        super(message);
//        this.errorCode = errorCode;
//        this.status = status;
//    }

}
