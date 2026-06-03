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

    private final int rawStatusCode;

    private final String rawResponseBody;



    // Throwable 은 별도로 필요할 때 생성자를 추가하든, 여기에 합치든 고민.
    // errorCode를 통해서, 외부 엔진 에러인지, 비즈니스 에러인지 등 구분.
    public ExternalEngineException(ErrorCode errorCode) {
//        super(errorCode.getMessage());
//        this.errorCode = errorCode;
        this(errorCode, null, 500, "Internal System Error");
    }

    // 2026/06/03
    public ExternalEngineException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, cause, 500, null);
    }

    // 2026/06/01 추가
    // instanceOf로 종속시키기 곤란하다면, 아예 예외 호출 당시에 관련 에러들을 대입해서 에러로 보여주는 게...
    public ExternalEngineException(ErrorCode errorCode, Throwable cause, int rawStatusCode, String rawResponseBody) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.rawStatusCode = rawStatusCode;
        this.rawResponseBody = rawResponseBody;
    }

}
