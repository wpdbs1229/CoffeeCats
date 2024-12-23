package org.hidevelop.coffeecats.exception.error;

import org.springframework.http.HttpStatus;

public interface CustomError {

    String getMessage();

    HttpStatus getHttpStatus();

}
