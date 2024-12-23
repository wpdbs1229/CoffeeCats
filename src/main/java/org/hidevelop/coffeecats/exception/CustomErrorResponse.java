package org.hidevelop.coffeecats.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {


    private String message;
    private HttpStatus httpStatus;

    public static ResponseEntity<CustomErrorResponse> toResponseEntity(CustomException customException) {
        return ResponseEntity.status(customException.getHttpStatus())
                .body(CustomErrorResponse.builder()
                        .message(customException.getMessage())
                        .httpStatus(customException.getHttpStatus())
                        .build());
    }
}
