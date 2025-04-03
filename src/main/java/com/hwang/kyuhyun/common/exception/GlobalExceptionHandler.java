package com.hwang.kyuhyun.common.exception;

import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppCustomException.class)
    public ResponseEntity<CommonResponse<Void>> handleException(AppCustomException e) {
        CommonResponse<Void> response = CommonResponse.fail(e.getErrorCode());

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<Void> handleException(Exception e) {
        return CommonResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
