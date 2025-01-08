package org.hidevelop.coffeecats.exception.error.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GoogleCommunicationError implements CustomError {
    // 에러메시지는 불친절해야해여
    EXTERNAL_SERVER_FAILED(HttpStatus.BAD_REQUEST, "외부 서버 요청 실패"),
    ;

    private final HttpStatus httpStatus;
    private final String message;



}
