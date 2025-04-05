package com.hwang.kyuhyun.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    EXTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "Csv 파일을 읽는 도중 문제가 발생했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "잘못된 파라미터 타입입니다."),
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
    TOO_FAST_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "이전 요청을 처리하고 있습니다. 10초 후 다시 시도해주세요"),
    NOT_FOUNT_LABEL(HttpStatus.BAD_REQUEST, "해당 통신판매업 컬럼이 존재하지 않습니다."),
    CVS_NOT_VALIDATED(HttpStatus.INTERNAL_SERVER_ERROR, "유효한 CVS 파일이 아닙니다."),
    NOT_FOUNT_ADDRESS(HttpStatus.NOT_FOUND, "해당 주소로 행정코드를 찾을 수 없습니다."),
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
