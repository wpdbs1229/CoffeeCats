package org.hidevelop.coffeecats.exception;

import lombok.Getter;
import org.hidevelop.coffeecats.exception.error.CustomError;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public CustomException(CustomError customError) {
        super(customError.getMessage());
        this.message = customError.getMessage();
        this.httpStatus = customError.getHttpStatus();

    }
}
