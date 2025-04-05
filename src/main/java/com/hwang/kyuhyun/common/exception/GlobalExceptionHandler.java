package com.hwang.kyuhyun.common.exception;

import com.hwang.kyuhyun.common.enums.ErrorCode;
import com.hwang.kyuhyun.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppCustomException.class)
    public ResponseEntity<CommonResponse<Void>> handleException(AppCustomException e) {
        CommonResponse<Void> response = CommonResponse.fail(e.getErrorCode());
        log.error("AppCustomException message : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class , BindException.class})
    public ResponseEntity<CommonResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("GlobalExceptionHandler message : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
                .body(CommonResponse.fail(ErrorCode.INVALID_INPUT_VALUE));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleException(Exception e) {
        log.error("Exception message : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(CommonResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
