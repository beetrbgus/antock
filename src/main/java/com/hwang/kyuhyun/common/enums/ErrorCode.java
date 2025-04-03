package com.hwang.kyuhyun.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "잘못된 파라미터 타입입니다."),
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
    TOO_FAST_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "이전 요청을 처리하고 있습니다. 10초 후 다시 시도해주세요"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public static ErrorCode findByStatus(int statusCode) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.httpStatus.value() == statusCode)
                .findFirst()
                .orElse(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
