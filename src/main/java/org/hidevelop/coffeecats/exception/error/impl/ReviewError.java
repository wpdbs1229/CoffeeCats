package org.hidevelop.coffeecats.exception.error.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewError implements CustomError {

    DOES_NOT_EXIST_REVIEW(HttpStatus.BAD_REQUEST, "리뷰가 없습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
