package org.hidevelop.coffeecats.exception.error.impl;

import lombok.Getter;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberCustomError implements CustomError {

    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Member already exists"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    MemberCustomError(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }


}
