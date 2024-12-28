package org.hidevelop.coffeecats.model.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hidevelop.coffeecats.model.validator.validator.LongitudeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LongitudeValidator.class)
@Target({ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLongitude {
    String message() default "경도는 124.5에서 131.87 사이의 값이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
