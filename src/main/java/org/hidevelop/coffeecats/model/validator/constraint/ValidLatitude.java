package org.hidevelop.coffeecats.model.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hidevelop.coffeecats.model.validator.validator.LatitudeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ElementType.RECORD_COMPONENT, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLatitude {
    String message() default "위도 33.1에서 38.61 사이의 값이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
