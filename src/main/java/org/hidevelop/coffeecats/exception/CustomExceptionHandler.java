package org.hidevelop.coffeecats.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.hidevelop.coffeecats.model.type.CafeType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e){
        return CustomErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException e){
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(violation ->
                errors.put(
                        violation.getPropertyPath().toString(), //fieldName
                        violation.getMessage()) //errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
       Map<String, String> errors = new HashMap<>();

       Throwable cause = e.getCause();
       if (cause instanceof InvalidFormatException invalidFormatException) {
           String fieldName = invalidFormatException.getPath().get(0).getFieldName();;
           String invalidValue = invalidFormatException.getValue().toString();
           String message = String.format("%s는 유효하지 않은 값입니다. 허용되는 값: %s",
                   invalidValue,
                   Arrays.toString(CafeType.values()));

           errors.put(fieldName, message);
       } else {
           errors.put("error", "잘못된 요청입니다.");
       }

       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse("유효 하지 않은 값입니다.", errorMessages);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
