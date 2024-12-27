package org.hidevelop.coffeecats.exception.error.impl;

import lombok.Getter;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberCustomError implements CustomError {

    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 회원가입된 회원입니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원가입을 진행해주세요"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    MemberCustomError(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }


}
