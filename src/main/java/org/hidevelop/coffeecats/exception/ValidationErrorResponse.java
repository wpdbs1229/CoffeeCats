package org.hidevelop.coffeecats.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private List<String> details;
}
