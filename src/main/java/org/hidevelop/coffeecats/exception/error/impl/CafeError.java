package org.hidevelop.coffeecats.exception.error.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CafeError implements CustomError {

    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 카페입니다."),
    DOES_NOT_EXIST_CAFE_TYPE(HttpStatus.BAD_REQUEST, "존재하지 않은 카페타입 입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
