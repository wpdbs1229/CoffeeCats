package org.hidevelop.coffeecats.exception.error.impl;

import lombok.Getter;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthenticationError implements CustomError {
    // 에러메시지는 불친절해야해여
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 접근입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 접근입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED,"잘못된 접근입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    AuthenticationError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
