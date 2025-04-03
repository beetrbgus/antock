package com.hwang.kyuhyun.common.exception;

import com.hwang.kyuhyun.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class AppCustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppCustomException(ErrorCode  errorCode) {
        this.errorCode = errorCode;
    }

    public AppCustomException(int  statusCode) {
        this.errorCode = ErrorCode.findByStatus(statusCode);
    }

    public String getMessage() {
        return errorCode.getMessage();
    }
}