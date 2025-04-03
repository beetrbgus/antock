package com.hwang.kyuhyun.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hwang.kyuhyun.common.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T> (
        @JsonIgnore
        HttpStatus httpStatus,
        boolean success,
        @Nullable T data,
        @Nullable String errorMessage
) {
    public static <T> CommonResponse<T> ok(@Nullable final T data) {
        return new CommonResponse<>(HttpStatus.OK, true, data, null);
    }

    public static <T> CommonResponse<T> created(@Nullable final T data) {
        return new CommonResponse<>(HttpStatus.CREATED, true, data, null);
    }

    public static <T> CommonResponse<T> fail(final ErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getHttpStatus(), false, null, errorCode.getMessage());
    }
}
